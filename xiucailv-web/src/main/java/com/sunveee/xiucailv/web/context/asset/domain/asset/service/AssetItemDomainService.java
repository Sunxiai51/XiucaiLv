package com.sunveee.xiucailv.web.context.asset.domain.asset.service;

import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetItem;
import com.sunveee.xiucailv.web.context.asset.domain.asset.repository.AssetItemJdbcRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AssetItemDomainService
 *
 * @author SunVeee
 * @date 2022/12/3 23:15
 */
@Service
public class AssetItemDomainService {
    @Autowired
    private AssetItemJdbcRepository assetItemJdbcRepository;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void persist(List<AssetItem> assetItems) {
        if (CollectionUtils.isEmpty(assetItems)) {
            return;
        }
        for (AssetItem assetItem : assetItems) {
            assetItem.validate();
            assetItemJdbcRepository.saveOrUpdateByCode(assetItem);
        }

    }

    public List<AssetItem> getAllAssetItems() {
        return assetItemJdbcRepository.queryAll();
    }
}
