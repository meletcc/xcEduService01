package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 课程、图片关联
 */
public interface CoursePicRepository extends JpaRepository<CoursePic, String> {
    // 删除课程图片：当返回值大于0，表示删除成功的记录数
    long deleteByCourseid(String courseId);
}
