package com.sunveee.xiucailv.web.context.asset.application;

import com.sunveee.xiucailv.web.context.asset.application.handler.CSVReader;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetItem;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetSnapshot;
import com.sunveee.xiucailv.web.context.asset.domain.asset.service.AssetItemDomainService;
import com.sunveee.xiucailv.web.context.asset.domain.asset.service.AssetSnapshotDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

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
        List<AssetItem> assetItems = null;
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
        List<AssetSnapshot> assetSnapshots = null;
        try {
            assetSnapshots = CSVReader.readAssetSnapshotFromCsv(new FileInputStream(csvFile), date);
        } catch (Exception e) {
            throw new RuntimeException(String.format("read AssetSnapshot from csvFile[%s] with date[%s] exception", csvFilePath, date), e);
        }
        assetSnapshotDomainService.persist(assetSnapshots);
    }

}
