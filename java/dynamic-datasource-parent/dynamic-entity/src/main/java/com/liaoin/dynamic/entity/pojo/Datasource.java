package com.liaoin.dynamic.entity.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Datasource
 *
 * @author cqwu729
 * @date 2018/12/18 13:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.camelhump)
@Table(name = "t_datasource")
public class Datasource {

    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("驱动")
    private String driver;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
