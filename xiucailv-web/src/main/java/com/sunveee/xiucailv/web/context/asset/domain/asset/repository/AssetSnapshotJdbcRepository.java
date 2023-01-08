package com.sunveee.xiucailv.web.context.asset.domain.asset.repository;

import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetSnapshot;
import com.sunveee.xiucailv.web.context.asset.domain.asset.repository.dao.AssetSnapshotDao;
import com.sunveee.xiucailv.web.context.asset.domain.asset.repository.po.AssetSnapshotPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AssetSnapshotJdbcRepository
 *
 * @author SunVeee
 * @date 2023/1/5 21:41
 */
@Repository
public class AssetSnapshotJdbcRepository {
    @Autowired
    private AssetSnapshotDao assetSnapshotDao;

    public void save(AssetSnapshot assetSnapshot) {
        assetSnapshotDao.save(AssetSnapshotPO.fromDomain(assetSnapshot));
    }

    public List<AssetSnapshot> findByDateStartsWithAndUsername(String datePrefix, String username) {
        return assetSnapshotDao.findByDateStartsWithAndUsername(datePrefix, username)
                .stream().map(AssetSnapshotPO::toDomain).collect(Collectors.toList());
    }
}
