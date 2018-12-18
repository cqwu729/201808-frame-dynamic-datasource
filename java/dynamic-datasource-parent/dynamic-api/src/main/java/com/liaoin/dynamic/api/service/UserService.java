package com.liaoin.dynamic.api.service;

import com.liaoin.dynamic.entity.pojo.User;

import java.util.List;

/**
 * UserService
 *
 * @author cqwu729
 * @date 2018/12/18 13:44
 */
public interface UserService {
    List<User> queryAllUserLists();
}
