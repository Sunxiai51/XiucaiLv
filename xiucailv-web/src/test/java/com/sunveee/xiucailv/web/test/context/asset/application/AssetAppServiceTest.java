package com.sunveee.xiucailv.web.test.context.asset.application;

import com.sunveee.xiucailv.web.XiucaiLvWebMain;
import com.sunveee.xiucailv.web.context.asset.application.AssetAppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

/**
 * AssetAppServiceTest
 *
 * @author SunVeee
 * @date 2023/1/5 20:26
 */
@Transactional
@Rollback(value = false)
@SpringBootTest(classes = XiucaiLvWebMain.class)
public class AssetAppServiceTest {
    @Autowired
    private AssetAppService assetAppService;

    @Test
    public void importAssetItem() throws FileNotFoundException {
        String path = "/Users/veee/code/git_repository_51/XiucaiLv/xiucailv-web/src/test/resources/test-data/51_asset_item.csv";
        assetAppService.importAssetItem(path);
    }

    @Test
    public void importAssetSnapshot() throws FileNotFoundException {
        String path = "/Users/veee/code/git_repository_51/XiucaiLv/xiucailv-web/src/test/resources/test-data/51_asset_snapshot_20230103.csv";
        assetAppService.importAssetSnapshot(path, "20221130");
        assetAppService.importAssetSnapshot(path, "20230103");
    }

    @Test
    public void generateMonthlyAssetReport() {
        assetAppService.generateMonthlyAssetReport(2022, 12, "51", "20230103");
    }

}
