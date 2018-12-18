package com.liaoin.dynamic.api.service.impl;

import com.liaoin.dynamic.api.config.DynamicDataSource;
import com.liaoin.dynamic.api.config.DynamicDataSourceContextHolder;
import com.liaoin.dynamic.api.service.DatasourceService;
import com.liaoin.dynamic.entity.pojo.Datasource;
import com.liaoin.dynamic.mybatis.DatasourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DatasourceServiceImpl
 *
 * @author cqwu729
 * @date 2018/12/18 13:53
 */

@Service
@Slf4j
public class DatasourceServiceImpl implements DatasourceService {

    @Resource
    private DatasourceMapper datasourceMapper;

    //用户自定义数据源
    private Map<String, DataSource> slaveDataSources = new HashMap<>();

    @Autowired
    private ApplicationContext ctx;

    //默认数据源
    private DataSource defaultDataSource;

    @Resource
    private Environment environment;

    @Override
    public void register() {
        //基础数据源
        initDefaultDataSource();
        //数据库动态数据源
        initslaveDataSources();
        //获取BeanFactory
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) ctx.getAutowireCapableBeanFactory();
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();

        //添加默认数据源
        targetDataSources.put("dataSource", this.defaultDataSource);
        if(!DynamicDataSourceContextHolder.isContainsDataSource("dataSource")){
            DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        }
        //添加其他数据源
        targetDataSources.putAll(slaveDataSources);
        for (String key : slaveDataSources.keySet()) {
            if(!DynamicDataSourceContextHolder.isContainsDataSource(key)){
                DynamicDataSourceContextHolder.dataSourceIds.add(key);
            }
        }

        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        //注册 - BeanDefinitionRegistry
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
    }

    //自定义数据源
    private void initslaveDataSources() {
        // 读取配置文件获取更多数据源
        List<Datasource> datasources = datasourceMapper.selectAll();
        // 多个数据源
        for (Datasource datasource : datasources){
            Map<String, Object> dsMap = new HashMap<>();
            dsMap.put("driver", datasource.getDriver());
            dsMap.put("url", datasource.getUrl());
            dsMap.put("username", datasource.getUsername());
            dsMap.put("password", datasource.getPassword());
            DataSource ds = buildDataSource(dsMap);
            if(ds != null){
                slaveDataSources.put(datasource.getId()+"", ds);
            }
        }
    }

    //默认数据源
    private void initDefaultDataSource() {
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", environment.getProperty("spring.datasource.driver"));
        dsMap.put("url", environment.getProperty("spring.datasource.url"));
        dsMap.put("username", environment.getProperty("spring.datasource.username"));
        dsMap.put("password", environment.getProperty("spring.datasource.password"));
        defaultDataSource = buildDataSource(dsMap);
    }

    public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        try {
            Object type = dataSourceMap.get("type");
            if (type == null) {
                type = "com.zaxxer.hikari.HikariDataSource";// 默认DataSource
            }
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dataSourceMap.get("driver").toString();
            String url = dataSourceMap.get("url").toString();
            String username = dataSourceMap.get("username").toString();
            String password = dataSourceMap.get("password").toString();
            // 自定义DataSource配置
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
