package com.sunveee.xiucailv.web.context.asset.domain.asset.repository;

import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetItem;
import com.sunveee.xiucailv.web.context.asset.domain.asset.repository.dao.AssetItemDao;
import com.sunveee.xiucailv.web.context.asset.domain.asset.repository.po.AssetItemPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AssetItemJdbcRepository
 *
 * @author SunVeee
 * @date 2022/12/3 23:13
 */
@Repository
public class AssetItemJdbcRepository {
    @Autowired
    private AssetItemDao assetItemDao;

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveOrUpdateByCode(AssetItem assetItem) {
        AssetItemPO exists = assetItemDao.getByCodeForUpdate(assetItem.getCode());
        if (null == exists) {
            assetItemDao.save(AssetItemPO.fromDomain(assetItem));
        } else {
            AssetItemPO updateInfo = AssetItemPO.fromDomain(assetItem);
            exists.setName(updateInfo.getName());
            exists.setLabelsJson(updateInfo.getLabelsJson());
            exists.setDescription(updateInfo.getDescription());
            assetItemDao.save(exists);
        }
    }

    public List<AssetItem> queryAll() {
        return assetItemDao.findAll().stream().map(AssetItemPO::toDomain).collect(Collectors.toList());
    }
}
