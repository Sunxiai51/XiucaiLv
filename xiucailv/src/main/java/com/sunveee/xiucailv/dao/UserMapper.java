package com.sunveee.xiucailv.dao;

import java.util.List;

import com.sunveee.xiucailv.domain.User;
import com.sunveee.xiucailv.util.PageEntity;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectUserPage(PageEntity pageEntity);

    Integer countAll();
}