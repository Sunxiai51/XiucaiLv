package com.sunveee.xiucailv.web.context.asset.domain.asset.entity;

import com.sunveee.framework.common.exceptions.utils.BizAssertUtil;
import com.sunveee.framework.common.utils.json.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String code;
    private String assetItemCode;
    private AssetItem.AssetItemType assetItemType;
    private Time time;
    private String userid;

    private long balance;
    private Currency currency;

    private String ext;

    public DebtExt debtExt() {
        BizAssertUtil.isTrue(this.assetItemType == AssetItem.AssetItemType.DEBT, "type must match DEBT when execute debtExt()");
        return JSONUtils.parseObject(ext, DebtExt.class);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Time {
        public int year;
        public int month;
        public int day;
        private long timestamp;
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
            private long repayDate;
            private long balance;
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
        private long profit;

    }


}
