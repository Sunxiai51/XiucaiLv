package com.sunveee.xiucailv.web.context.asset.domain.asset.repository.dao;

import com.sunveee.xiucailv.web.context.asset.domain.asset.repository.po.AssetItemPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

/**
 * AssetItemDao
 *
 * @author SunVeee
 * @date 2022/12/3 23:14
 */
@Repository
public interface AssetItemDao extends JpaRepository<AssetItemPO, Long> {

    AssetItemPO getByCode(String code);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select item from AssetItemPO item where item.code = :code")
    AssetItemPO getByCodeForUpdate(@Param("code") String code);


}
