package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 这俩注解：启动测试类会从main下找springBoot启动类，加载spring容器
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    // 分页查询
    @Test
    public void testFindPage() {
        // 分页参数
        int page = 1;// 从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all.getTotalPages());
    }

    // 自定义分页、条件查询测试
    @Test
    public void testFindAllByExample() {
        // 分页参数
        int page = 0;// 从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        // 条件值对象
        CmsPage cmsPage = new CmsPage();
        // 要查询5a751fab6abb5044e0d19ea1站点的页面
//        cmsPage.setSiteId("5b30b052f58b4411fc6cb1cf");
        // 设置模板id条件
//        cmsPage.setTemplateId("5ad9a24d68db5239b8fef199");
        // 设置页面别名
        cmsPage.setPageAliase("轮播");
        // 条件匹配器
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含关键字
//        ExampleMatcher.GenericPropertyMatchers.startsWith()// 前缀匹配
        // 定义Example
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }

    // 添加
    @Test
    public void testInsert() {
        // 定义实体类     
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    // 删除
    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("5da688c07e761a3194a5d0cb");
    }

    /**
     * 修改
     * Optional是jdk1.8引入的类型，Optional是一个容器对象，
     * 它包括了我们需要的对象，使用isPresent方法判断 所包含对象是否为空
     * isPresent方法返回false则表示Optional包含对象为空
     * 否则可以使用get()取出对象进行操作。
     * Optional的优点是：
     * 1、提醒你非空判断
     * 2、将对象非空检测标准化
     */
    @Test
    public void testUpdate() {
        // 查询对象
        Optional<CmsPage> optional = cmsPageRepository.findById("5da688c07e761a3194a5d0cb");
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            //设置要修改值
            cmsPage.setPageAliase("test01");
            //...
            //修改
            CmsPage save = cmsPageRepository.save(cmsPage);
            System.out.println(save);
        }
    }

    // 根据页面名称查询
    @Test
    public void testfindByPageName() {
        CmsPage cmsPage = cmsPageRepository.findByPageName("测试页面");
        System.out.println(cmsPage);
    }
}
