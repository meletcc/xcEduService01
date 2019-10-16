package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.CourseBase;
import lombok.Data;
import lombok.ToString;

/**
 * 我的课程返回扩展类：课程基本信息表 + 课程图片表
 */
@Data
@ToString
public class CourseInfo extends CourseBase {
    // 课程图片，存在fastDfs中
    private String pic;
}
