package com.sunveee.xiucailv.web.context.asset.domain.asset.repository;

import com.sunveee.framework.common.utils.json.JSONUtils;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetSnapshot;
import org.springframework.stereotype.Repository;

/**
 * AssetSnapshotJdbcRepository
 *
 * @author SunVeee
 * @date 2023/1/5 21:41
 */
@Repository
public class AssetSnapshotJdbcRepository {

    public void saveOrUpdateByCode(AssetSnapshot assetSnapshot) {
        System.out.println("save assetSnapshot: " + JSONUtils.toJSONString(assetSnapshot));
    }
}
