package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 作为查询返回值的扩展类，不是和数据库表一一对应的
 * 课程计划返回值需要树形结构
 */
@Data
@ToString
public class TeachplanNode extends Teachplan {

    // 子节点
    List<TeachplanNode> children;

    // 媒资文件id
    String mediaId;

    // 媒资文件原始名称
    String mediaFileoriginalname;

}
