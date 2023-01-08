package com.sunveee.xiucailv.web.context.asset.application;

import com.sunveee.framework.common.exceptions.utils.BizAssertUtil;
import com.sunveee.xiucailv.web.context.asset.application.handler.CSVReader;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetItem;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetSnapshot;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.MonthlyAssetReport;
import com.sunveee.xiucailv.web.context.asset.domain.asset.service.AssetItemDomainService;
import com.sunveee.xiucailv.web.context.asset.domain.asset.service.AssetSnapshotDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AssetAppService
 *
 * @author SunVeee
 * @date 2022/12/3 23:52
 */
@Service
public class AssetAppService {
    @Autowired
    private AssetItemDomainService assetItemDomainService;
    @Autowired
    private AssetSnapshotDomainService assetSnapshotDomainService;

    @Transactional(rollbackFor = Exception.class)
    public void importAssetItem(String csvFilePath) {
        final File csvFile = new File(csvFilePath);
        List<AssetItem> assetItems;
        try {
            assetItems = CSVReader.readAssetItemFromCsv(new FileInputStream(csvFile));
        } catch (Exception e) {
            throw new RuntimeException(String.format("read AssetItem from csvFile[%s] exception", csvFilePath), e);
        }
        assetItemDomainService.persist(assetItems);
    }

    @Transactional(rollbackFor = Exception.class)
    public void importAssetSnapshot(String csvFilePath, String date) {
        List<AssetItem> assetItems = assetItemDomainService.getAllAssetItems();
        final File csvFile = new File(csvFilePath);
        List<AssetSnapshot> assetSnapshots;
        try {
            assetSnapshots = CSVReader.readAssetSnapshotFromCsv(new FileInputStream(csvFile), date);
            assetSnapshots.forEach(x -> {
                Optional<AssetItem> assetItem = assetItems.stream().filter(item -> item.getCode().equals(x.getAssetItemCode())).findAny();
                BizAssertUtil.isTrue(assetItem.isPresent(), "unknown AssetSnapshot's assetItemCode", x.getAssetItemCode());
                x.fillWithAssetItem(assetItem.get());
            });
        } catch (Exception e) {
            throw new RuntimeException(String.format("read AssetSnapshot from csvFile[%s] with date[%s] exception", csvFilePath, date), e);
        }
        assetSnapshotDomainService.persist(assetSnapshots);
    }

    @Transactional(rollbackFor = Exception.class)
    public void generateMonthlyAssetReport(int year, int month, String username) {
        List<AssetSnapshot> assetSnapshots = assetSnapshotDomainService.queryByYearMonthUsername(year, month, username);
        Set<Currency> currencySet = assetSnapshots.stream().map(AssetSnapshot::getCurrency).collect(Collectors.toSet());
        for (Currency currency : currencySet) {
            MonthlyAssetReport report = MonthlyAssetReport.builder()
                    .year(year)
                    .month(month)
                    .username(username)
                    .currency(currency)
                    .build();
            report.calculate(assetSnapshots.stream().filter(x -> x.getCurrency() == currency).collect(Collectors.toList()));
            report.print();
        }

    }

}
