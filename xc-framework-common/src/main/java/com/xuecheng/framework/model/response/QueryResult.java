package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 查询列表结果
 */
@Data
@ToString
public class QueryResult<T> {
    // 数据列表
    private List<T> list;
    // 数据总数
    private long total;
}
