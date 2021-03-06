package com.xuecheng.framework.domain.course.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * 我的课程查询条件
 */
@Data
@ToString
public class CourseListRequest extends RequestData {
    // 公司Id
    private String companyId;
}
