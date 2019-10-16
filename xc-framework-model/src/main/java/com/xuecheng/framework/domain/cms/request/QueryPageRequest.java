package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 自定义接收页面查询的查询条件
 * 为后期扩展需求，请求类型统一继承 RequestData类型
 **/
@Data
public class QueryPageRequest extends RequestData {

    @ApiModelProperty("站点id")
    private String siteId;

    @ApiModelProperty("页面id")
    private String pageId;

    @ApiModelProperty("页面名称")
    private String pageName;

    @ApiModelProperty("页面别名")
    private String pageAliase;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("页面类型")
    private String pageType;

    // 其他查询条件

}