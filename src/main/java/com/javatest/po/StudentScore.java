package com.javatest.po;

import java.io.Serializable;

/**
 * (StudentScore)实体类
 *
 * @author makejava
 * @since 2019-09-30 11:07:00
 */
public class StudentScore implements Serializable {
    private static final long serialVersionUID = 672128092584905440L;
    //学生id
    private Long id;
    //学生姓名
    private String name;
    //学生成绩
    private Integer score;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null: name.trim();
    }
    
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}