package com.sunveee.xiucailv.service;

import java.util.List;

import com.sunveee.xiucailv.domain.User;

public interface UserService {
    public User getUserById(int userId);

    public List<User> getUserPage(int pageNo, int pageSize);

    public Integer getAllUserCount();
}
