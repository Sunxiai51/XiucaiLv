package com.sunveee.xiucailv.web.context.asset.domain.asset.entity;

import com.sunveee.framework.common.exceptions.utils.BizAssertUtil;
import com.sunveee.framework.common.utils.datetime.LocalDateTimeTransferUtils;
import com.sunveee.framework.common.utils.json.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.List;

/**
 * AssetSnapshot
 *
 * @author SunVeee
 * @date 2022/12/4 15:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetSnapshot {
    private String date;
    private String assetItemCode;
    private double balance;
    private Currency currency;
    private String ext;

    // 以下为冗余字段
    private AssetItem.AssetItemType assetItemType;
    private String username;

    public static AssetSnapshot fromCsvRecord(CSVRecord csvRecord, String date) {
        return AssetSnapshot.builder()
                .date(date)
                .assetItemCode(csvRecord.get("asset_item_code"))
                .balance(Double.parseDouble(csvRecord.get(String.join("", date, "balance"))))
                .currency(Currency.getInstance(csvRecord.get("currency")))
                .ext(csvRecord.get(String.join("", date, "ext")))
                .build();
    }

    public DebtExt debtExt() {
        BizAssertUtil.isTrue(this.assetItemType == AssetItem.AssetItemType.DEBT, "type must match DEBT when execute debtExt()");
        return JSONUtils.parseObject(ext, DebtExt.class);
    }

    public void validate() {
        BizAssertUtil.hasText(assetItemCode, "assetItemCode cannot be null");
        BizAssertUtil.hasText(date, "date cannot be null");
        BizAssertUtil.notNull(currency, "currency cannot be null");
        // BizAssertUtil.notNull(assetItemType, "assetItemType cannot be null");
        // BizAssertUtil.hasText(username, "username cannot be null");

    }

    public int year() {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return localDate.getYear();
    }

    public int month() {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return localDate.getMonthValue();
    }

    public int day() {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return localDate.getDayOfMonth();
    }


    /**
     * 负债类资产扩展信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebtExt {
        private List<RepayPlanItem> repayPlan;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RepayPlanItem {
            private String repayDate;
            private double balance;

            public long repayTimestamp() {
                LocalDateTime localDateTime = LocalDateTime.parse(repayDate + "000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                return LocalDateTimeTransferUtils.toTimestamp(localDateTime);
            }
        }
    }

    /**
     * 投资类资产扩展信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvestExt {
        /**
         * 收益(浮动)
         */
        private double profit;

    }


}
