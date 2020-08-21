package com.javatest.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 使用JRS提供restful风格的接口
 */
@XmlRootElement(name = "studentScoreJRS")
public class StudentScore4JRS implements Serializable {
    private static final long serialVersionUID = 672128092584905440L;

    public StudentScore4JRS() {
    }

    public StudentScore4JRS(@NotNull(message = "id不能为空！") Long id, @NotBlank(message = "名字不能为空！") String name, @NotNull(message = "成绩不能为空！") Integer score) {
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

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null: name.trim();
    }

    @XmlElement
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