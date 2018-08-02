package com.sunveee.xiucailv.dao;

import com.sunveee.xiucailv.domain.Snapshot;

public interface SnapshotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Snapshot record);

    int insertSelective(Snapshot record);

    Snapshot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Snapshot record);

    int updateByPrimaryKey(Snapshot record);
}