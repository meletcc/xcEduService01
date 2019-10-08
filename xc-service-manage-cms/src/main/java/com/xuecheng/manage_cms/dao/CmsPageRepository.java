package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 使用Spring Data Mongodb完成Mongodb数据库的操作，Spring Data Mongodb提供一套快捷操作 mongodb的方法
 * 继承 MongoRepository，并指定实体类型和主键类型
 * MongoRepository中定义了很多现成的方法，如save、delete等
 * 同Spring Data JPA一样Spring Data mongodb也提供自定义方法的规则：
 * 按照ﬁndByXXX，ﬁndByXXXAndYYY、countByXXXAndYYY等规则定义方法，实现查询操作。
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    //根据页面名称查询
    CmsPage findByPageName(String pageName);

    //根据页面名称和类型查询     
    CmsPage findByPageNameAndPageType(String pageName, String pageType);

    //根据站点和页面类型查询记录数     
    int countBySiteIdAndPageType(String siteId, String pageType);

    //根据站点和页面类型分页查询
//    Page<CmsPage> findBySiteIdAndPageType(String siteId, String pageType, Pageable pageable);
}
