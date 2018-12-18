package com.liaoin.dynamic.api.service.impl;

import com.liaoin.dynamic.api.service.UserService;
import com.liaoin.dynamic.entity.pojo.User;
import com.liaoin.dynamic.mybatis.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserServiceImpl
 *
 * @author cqwu729
 * @date 2018/12/18 13:44
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> queryAllUserLists() {
        return userMapper.selectAll();
    }
}
