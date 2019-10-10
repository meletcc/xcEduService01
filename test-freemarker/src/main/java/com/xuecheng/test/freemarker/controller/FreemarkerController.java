package com.xuecheng.test.freemarker.controller;

import com.xuecheng.test.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 1、注释，即<#‐‐和‐‐>，介于其之间的内容会被freemarker忽略
 * 2、插值（Interpolation）：即${..}部分,freemarker会用真实的值代替${..}
 * 3、FTL指令：和HTML标记类似，名字前加#予以区分，Freemarker会解析标签中的表达式或逻辑。
 * 4、文本，仅文本信息这些不是freemarker的注释、插值、FTL指令的内容会被freemarker忽略解析，直接输出内容。
 */
@RequestMapping("/freemarker")
@Controller// 此时不能用RestController。否则会返回json数据
public class FreemarkerController {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 远程http调用接口，取到轮播图模型数据，放入形参的map当中
     */
    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map) {
        String dataUrl = "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        map.put("model", body);
        return "index_banner";
    }

    /**
     * 参数 map 的键值用来对应模板中的标签 ${name}等
     */
    @RequestMapping("/test1")
    public String freemarker(Map<String, Object> map) {
        // 向数据模型放数据
        map.put("name", "黑马程序员");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
        stu2.setBirthday(new Date());
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        // 向数据模型放数据：<#list stus as stu> </#list>
        map.put("stus", stus);
        // 准备map数据
        HashMap<String, Student> stuMap = new HashMap<>();
        stuMap.put("stu1", stu1);
        stuMap.put("stu2", stu2);
        // 向数据模型放数据
        map.put("stu1", stu1);
        // 向数据模型放数据
        map.put("stuMap", stuMap);
        // 返回模板文件名称，模型文件在resources下templates目录中，test1.ftl格式
        return "test1";
    }
}
