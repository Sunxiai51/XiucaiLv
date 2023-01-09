package com.sunveee.xiucailv.web.context.asset.domain.report.entity;

import com.sunveee.framework.common.exceptions.utils.BizAssertUtil;
import com.sunveee.framework.common.utils.datetime.LocalDateTimeTransferUtils;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetItem;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetSnapshot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static com.sunveee.xiucailv.web.common.utils.MoneyUtils.fen2yuanString;

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
        BizAssertUtil.notEmpty(assetSnapshots, "assetSnapshots cannot be empty");
        this.assetSnapshots = assetSnapshots;

        // 资产统计
        assetStatistics();
        // 负债统计
        debtStatistics();
        this.calculated = true;
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
        System.out.println(prettyPrint());
    }

    public String prettyPrint() {
        return String.format("[%s - %s] %s年%s月 资产月报\n", username, currency.getCurrencyCode(), year, month) +
                "\t资产统计\n" +
                String.format("\t\t总资产 %s \t现金总额 %s \t理财总额 %s\n", fen2yuanString(this.getAssetStatistics().getTotal()), fen2yuanString(this.getAssetStatistics().getTotalCash()), fen2yuanString(this.getAssetStatistics().getTotalInvest())) +
                "\t负债统计\n" +
                String.format("\t\t总负债 %s \t短期负债 %s \t中期负债 %s \t长期负债 %s\n", fen2yuanString(-1 * this.getDebtStatistics().getTotalDebtBalance()), fen2yuanString(-1 * this.getDebtStatistics().getDebtBalanceIn1month()), fen2yuanString(-1 * this.getDebtStatistics().getDebtBalanceIn3month()), fen2yuanString(-1 * this.getDebtStatistics().getDebtBalanceAfter3month()));
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
         * 理财总额
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
         * 一个月内负债（短期负债）
         */
        private long debtBalanceIn1month;
        /**
         * 三个月内负债（中期负债）
         */
        private long debtBalanceIn3month;
        /**
         * 三个月外负债（长期负债）
         */
        private long debtBalanceAfter3month;
    }
}
