package com.sunveee.xiucailv.dao;

import com.sunveee.xiucailv.domain.AccountSnapshot;

public interface AccountSnapshotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccountSnapshot record);

    int insertSelective(AccountSnapshot record);

    AccountSnapshot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountSnapshot record);

    int updateByPrimaryKey(AccountSnapshot record);
}