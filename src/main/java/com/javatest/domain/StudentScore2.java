package com.javatest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * 使用lombok改造
 * 使用挺简单的，可以直接参考官方文档https://projectlombok.org/features/all
 */
@Data   // 包含@ToString, @EqualsAndHashCode, @Getter, @Setter和@RequiredArgsConstructor
@NoArgsConstructor  // 自动添加无参构造函数
@AllArgsConstructor // 自动添加含全部参数的构造函数
public class StudentScore2 implements Serializable {
    private static final long serialVersionUID = 672128092584905440L;
    //学生id
    @NonNull
    private Long id;
    //学生姓名
    @NonNull
    private String name;
    //学生成绩
    @NonNull
    private Integer score;

}