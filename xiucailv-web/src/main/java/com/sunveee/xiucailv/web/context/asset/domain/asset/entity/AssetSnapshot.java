package com.sunveee.xiucailv.web.context.asset.domain.asset.entity;

import com.sunveee.framework.common.exceptions.utils.BizAssertUtil;
import com.sunveee.framework.common.utils.datetime.LocalDateTimeTransferUtils;
import com.sunveee.framework.common.utils.json.JSONUtils;
import com.sunveee.xiucailv.web.common.utils.MoneyUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

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
    private long balance;
    private Currency currency;
    private String ext;

    // 以下为冗余字段
    private AssetItem.AssetItemType assetItemType;
    private String username;

    public static Optional<AssetSnapshot> fromCsvRecord(CSVRecord csvRecord, String date) {
        if (StringUtils.isBlank(csvRecord.get("asset_item_code"))) {
            return Optional.empty();
        }
        return Optional.of(AssetSnapshot.builder()
                .date(date)
                .assetItemCode(csvRecord.get("asset_item_code"))
                .balance(MoneyUtils.yuan2fen(Double.parseDouble(csvRecord.get(String.join("", date, "balance")))))
                .currency(Currency.getInstance(csvRecord.get("currency")))
                .ext(csvRecord.get(String.join("", date, "ext")))
                .build());
    }

    public DebtExt debtExt() {
        BizAssertUtil.isTrue(this.assetItemType == AssetItem.AssetItemType.DEBT, "type must match DEBT when execute debtExt()");
        return DebtExt.parse(this.ext);
    }

    public InvestExt investExt() {
        BizAssertUtil.isTrue(this.assetItemType == AssetItem.AssetItemType.INVEST, "type must match INVEST when execute investExt()");
        return InvestExt.parse(this.ext);
    }

    public void validate() {
        BizAssertUtil.hasText(assetItemCode, "assetItemCode cannot be null");
        BizAssertUtil.hasText(date, "date cannot be null");
        BizAssertUtil.notNull(currency, "currency cannot be null");
        BizAssertUtil.notNull(assetItemType, "assetItemType cannot be null");
        BizAssertUtil.hasText(username, "username cannot be null");

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

    public void fillWithAssetItem(AssetItem assetItem) {
        this.setAssetItemType(assetItem.getType());
        this.setUsername(assetItem.getUsername());
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

        public static DebtExt parse(String ext) {
            DebtExt result = JSONUtils.parseObject(ext, DebtExt.class);
            if (null == result) {
                result = DebtExt.builder().build();
            }
            if (null == result.getRepayPlan()) {
                result.setRepayPlan(new ArrayList<>());
            }
            return result;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RepayPlanItem {
            private String repayDate;
            private long balance;

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
        @Builder.Default
        private long profit = 0;

        public static InvestExt parse(String ext) {
            InvestExt result = JSONUtils.parseObject(ext, InvestExt.class);
            if (null == result) {
                result = InvestExt.builder().build();
            }
            return result;
        }

    }


}
