package com.liaoin.dynamic.api.controller;

import com.liaoin.dynamic.api.config.DynamicDataSource;
import com.liaoin.dynamic.api.config.DynamicDataSourceContextHolder;
import com.liaoin.dynamic.api.service.DatasourceService;
import com.liaoin.dynamic.api.service.UserService;
import com.liaoin.dynamic.entity.pojo.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController
 *
 * @author cqwu729
 * @date 2018/12/18 13:45
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DatasourceService datasourceService;

    @GetMapping("/queryAllUserLists")
    public List<User> queryAllUserLists() {
        List<User> list = userService.queryAllUserLists();
        return list;
    }

    @GetMapping("/queryAllUserLists2")
    public List<User> queryAllUserLists2() {
        DynamicDataSourceContextHolder.setDataSourceType("2");
        List<User> list = userService.queryAllUserLists();
        DynamicDataSourceContextHolder.clearDataSourceType();
        return list;
    }

    @GetMapping("/register")
    public String register() {
        datasourceService.register();
        return "ok";
    }

    @GetMapping("/conn")
    public String conn() {
        try {
            Connection Conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl11","cqwu729", "123456");
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }


}
