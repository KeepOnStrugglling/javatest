package com.javatest.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * (StudentScore)实体类
 *
 * @author makejava
 * @since 2019-09-30 11:07:00
 */
public class StudentScore implements Serializable {
    private static final long serialVersionUID = 672128092584905440L;

    public StudentScore() {
    }

    public StudentScore(@NotNull(message = "id不能为空！") Long id, @NotBlank(message = "名字不能为空！") String name, @NotNull(message = "成绩不能为空！") Integer score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    @NotNull(message = "id不能为空！")
    private Long id;
    @NotBlank(message = "名字不能为空！")
    private String name;
    @NotNull(message = "成绩不能为空！")
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

    @Override
    public String toString() {
        return "StudentScore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}