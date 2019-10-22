package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * 添加课程返回类型
 */
@Data
@ToString
public class AddCourseResult extends ResponseResult {

    private String courseid;

    public AddCourseResult(ResultCode resultCode, String courseid) {
        super(resultCode);
        this.courseid = courseid;
    }

}
