package com.sunveee.xiucailv.web.context.asset.domain.asset.entity;

import com.sunveee.framework.common.exceptions.utils.BizAssertUtil;
import com.sunveee.framework.common.utils.datetime.LocalDateTimeTransferUtils;
import com.sunveee.framework.common.utils.json.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * MonthlyAssetReport
 *
 * @author SunVeee
 * @date 2022/12/4 16:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyAssetReport {
    public int year;
    public int month;
    private Currency currency;
    private String username;

    @Builder.Default
    private boolean calculated = false;

    // 以下字段计算生成
    /**
     * 资产快照
     */
    private List<AssetSnapshot> assetSnapshots;
    /**
     * 资产统计
     */
    private AssetStatistics assetStatistics;
    /**
     * 负债统计
     */
    private DebtStatistics debtStatistics;


    public void calculate(List<AssetSnapshot> assetSnapshots) {
        // 初始化资产快照
        initAssetSnapshots(assetSnapshots);
        // 资产统计
        assetStatistics();
        // 负债统计
        debtStatistics();
        this.calculated = true;
    }

    private void initAssetSnapshots(List<AssetSnapshot> assetSnapshots) {
        BizAssertUtil.notEmpty(assetSnapshots, "assetSnapshots cannot be empty");
        Set<String> assetItemCodeSet = assetSnapshots.stream().map(AssetSnapshot::getAssetItemCode).collect(Collectors.toSet());
        this.assetSnapshots = new ArrayList<>(assetItemCodeSet.size());
        assetItemCodeSet.forEach(assetItemCode -> {
            AssetSnapshot assetSnapshot = assetSnapshots.stream().filter(x -> x.getAssetItemCode().equals(assetItemCode))
                    .filter(x -> x.year() == year
                            && x.month() == month
                            && x.getCurrency().getCurrencyCode().equals(currency.getCurrencyCode())
                            && x.getUsername().equals(username)
                    )
                    .max((o1, o2) -> (int) (o1.day() - o2.day())).get();
            this.assetSnapshots.add(assetSnapshot);
        });
    }

    private void assetStatistics() {
        long totalCash = 0, totalInvest = 0;
        for (AssetSnapshot assetSnapshot : assetSnapshots) {
            if (assetSnapshot.getAssetItemType() == AssetItem.AssetItemType.INVEST) {
                totalInvest += assetSnapshot.getBalance();
            }
            if (assetSnapshot.getAssetItemType() == AssetItem.AssetItemType.CASH) {
                totalCash += assetSnapshot.getBalance();
            }
        }
        this.assetStatistics = AssetStatistics.builder()
                .total(totalCash + totalInvest)
                .totalCash(totalCash)
                .totalInvest(totalInvest)
                .build();
    }

    private void debtStatistics() {
        LocalDateTime thisMonthLastDay = LocalDateTime.of(this.getYear(), this.getMonth(), 1, 0, 0, 0, 0).plusMonths(1);

        long totalDebtBalance = 0, debtBalanceIn1month = 0, debtBalanceIn3month = 0, debtBalanceAfter3month = 0;
        for (AssetSnapshot assetSnapshot : assetSnapshots) {
            if (assetSnapshot.getAssetItemType() == AssetItem.AssetItemType.DEBT) {
                totalDebtBalance += assetSnapshot.getBalance();
                for (AssetSnapshot.DebtExt.RepayPlanItem repayPlanItem : assetSnapshot.debtExt().getRepayPlan()) {
                    if (repayPlanItem.repayTimestamp() < LocalDateTimeTransferUtils.toTimestamp(thisMonthLastDay.plusMonths(1))) {
                        debtBalanceIn1month += repayPlanItem.getBalance();
                    }
                    if (repayPlanItem.repayTimestamp() < LocalDateTimeTransferUtils.toTimestamp(thisMonthLastDay.plusMonths(3))) {
                        debtBalanceIn3month += repayPlanItem.getBalance();
                    } else {
                        debtBalanceAfter3month += repayPlanItem.getBalance();
                    }
                }
            }
        }
        this.debtStatistics = DebtStatistics.builder()
                .totalDebtBalance(totalDebtBalance)
                .debtBalanceIn1month(debtBalanceIn1month)
                .debtBalanceIn3month(debtBalanceIn3month)
                .debtBalanceAfter3month(debtBalanceAfter3month)
                .build();
    }

    public void print() {
        System.out.printf("用户%s %s年%s月 资产月报(%s)%n", username, year, month, currency.getCurrencyCode());
        System.out.printf("  资产统计: %s%n", JSONUtils.toJSONString(this.getAssetStatistics()));
        System.out.printf("  负债统计: %s%n", JSONUtils.toJSONString(this.getDebtStatistics()));
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssetStatistics {
        /**
         * 总资产
         */
        private long total;
        /**
         * 现金总额
         */
        private long totalCash;
        /**
         * 理财资产总额
         */
        private long totalInvest;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebtStatistics {
        /**
         * 总负债
         */
        private long totalDebtBalance;
        /**
         * 一月内负债
         */
        private long debtBalanceIn1month;
        /**
         * 三个月内负债
         */
        private long debtBalanceIn3month;
        /**
         * 三个月外负债
         */
        private long debtBalanceAfter3month;
    }
}
