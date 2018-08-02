package com.sunveee.xiucailv.dao;

import com.sunveee.xiucailv.domain.AssetsEvent;

public interface AssetsEventMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AssetsEvent record);

    int insertSelective(AssetsEvent record);

    AssetsEvent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AssetsEvent record);

    int updateByPrimaryKey(AssetsEvent record);
}