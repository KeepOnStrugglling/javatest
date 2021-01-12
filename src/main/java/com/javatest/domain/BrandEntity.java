package com.javatest.domain;


import com.javatest.annotation.check.StatusValue;
import com.javatest.exception.valid.AddGroup;
import com.javatest.exception.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 * @author azure
 * @date 2020-09-02 16:27:19
 */
@Data
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @Null(message = "新增不需品牌id",groups = AddGroup.class)
    @NotNull(message = "修改必须指定品牌id",groups = UpdateGroup.class)
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotEmpty(groups = {AddGroup.class})
    @URL(message = "品牌logo地址必须为url",groups = {AddGroup.class,UpdateGroup.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @StatusValue
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotNull(groups = {AddGroup.class})
    @Pattern(regexp = "/^[a-zA-Z]$/",message = "检索首字母必须是一个字母",groups = {AddGroup.class,UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @Min(value = 0,message = "排序必须大于等于0",groups = {AddGroup.class,UpdateGroup.class})
    private Integer sort;

}
