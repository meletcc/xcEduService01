package com.xuecheng.framework.domain.filesystem;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * 文件类，对应mongodb的集合 filesystem
 */
@Data
@ToString
@Document(collection = "filesystem")
public class FileSystem {

    @Id
    private String fileId;// 就是文件的url
    // 文件请求路径
    private String filePath;
    // 文件大小
    private long fileSize;
    // 文件名称
    private String fileName;
    // 文件类型
    private String fileType;
    // 图片宽度
    private int fileWidth;
    // 图片高度
    private int fileHeight;

    /**
     * 以下的就是作为通用系统提供的通用字段，给其他系统存储一些系统各自的信息标识
     */
    // 用户id，用于授权
    private String userId;
    // 业务key: 课程管理会在此字段中存储课程id用于标识该图片属于哪个课程
    private String businesskey;
    // 业务标签: 文件系统服务会为使用文件系统服务的子系统分配文件标签，用于标识此文件来自哪个系统
    private String filetag;
    // 文件元信息
    private Map metadata;

}