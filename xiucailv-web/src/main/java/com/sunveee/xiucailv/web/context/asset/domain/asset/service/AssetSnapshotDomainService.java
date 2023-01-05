package com.sunveee.xiucailv.web.context.asset.domain.asset.service;

import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetSnapshot;
import com.sunveee.xiucailv.web.context.asset.domain.asset.repository.AssetSnapshotJdbcRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AssetSnapshotDomainService
 *
 * @author SunVeee
 * @date 2023/1/5 21:41
 */
@Service
public class AssetSnapshotDomainService {
    @Autowired
    private AssetSnapshotJdbcRepository assetSnapshotJdbcRepository;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void persist(List<AssetSnapshot> assetSnapshots) {
        if (CollectionUtils.isEmpty(assetSnapshots)) {
            return;
        }
        for (AssetSnapshot assetSnapshot : assetSnapshots) {
            assetSnapshot.validate();
            assetSnapshotJdbcRepository.saveOrUpdateByCode(assetSnapshot);
        }

    }
}
