package com.sunveee.xiucailv.web.context.asset.domain.asset.repository;

import com.sunveee.framework.common.utils.json.JSONUtils;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * AssetItemJdbcRepository
 *
 * @author SunVeee
 * @date 2022/12/3 23:13
 */
@Repository
public class AssetItemJdbcRepository {

    public void saveOrUpdateByCode(AssetItem assetItem) {
        System.out.println("save assetItem: " + JSONUtils.toJSONString(assetItem));
    }

    public List<AssetItem> queryAll() {
        return new ArrayList<>();
    }
}
