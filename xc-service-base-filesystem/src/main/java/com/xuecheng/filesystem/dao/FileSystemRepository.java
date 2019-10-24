package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 系统的文件信息（图片、文档等小文件的信息）在mongodb中存储
 */
public interface FileSystemRepository extends MongoRepository<FileSystem, String> {
}
