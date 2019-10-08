package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接收页面查询的查询条件类
 * 为后期扩展需求，请求类型统一继承RequestData类型
 **/
@Data
public class QueryPageRequest extends RequestData {

    //站点id
    @ApiModelProperty("站点id")
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //模版id
    private String templateId;
    //....
}
