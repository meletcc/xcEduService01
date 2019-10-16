package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * 查询：统一的响应数据类
 */
@Data
@ToString
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }

}
