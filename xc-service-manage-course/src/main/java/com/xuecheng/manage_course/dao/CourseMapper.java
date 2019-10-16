package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {

    // 通过课程id查询课程基本信息
    CourseBase findCourseBaseById(String id);

    // 测试pageHelper。查询课程信息列表
    Page<CourseBase> findCourseList();

    // 我的课程查询列表，不需要分页参数，由service层提供，返回值为 Page
    Page<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);
}
