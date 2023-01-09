package com.sunveee.xiucailv.web.context.asset.domain.asset.repository.dao;

import com.sunveee.xiucailv.web.context.asset.domain.asset.repository.po.AssetSnapshotPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AssetSnapshotDao
 *
 * @author SunVeee
 * @date 2023/1/8 22:16
 */
@Repository
public interface AssetSnapshotDao extends JpaRepository<AssetSnapshotPO, Long> {

    List<AssetSnapshotPO> findByDateStartsWithAndUsername(String date, String username);

    List<AssetSnapshotPO> findByDateAndUsername(String date, String username);
}
