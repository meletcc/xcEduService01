package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FileSystemService {

    @Autowired
    FileSystemRepository fileSystemRepository;

    // 读取配置文件fastDfs相关信息
    @Value("${xuecheng.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${xuecheng.fastdfs.charset}")
    String charset;
    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;

    public UploadFileResult upload(MultipartFile multipartFile,
                                   String fileTag,
                                   String businessKey,
                                   String metadata) {
        if (multipartFile == null) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        // 1.上传文件到fastdfs中，返回文件id
        String fileId = this.fdfs_upload(multipartFile);
        // 2.将文件id和其他文件信息存入mongodb
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFiletag(fileTag);
        fileSystem.setBusinesskey(businessKey);

        // 元数据要转换成Map
        if (StringUtils.isNotEmpty(metadata)) {
            Map map = JSON.parseObject(metadata, Map.class);
            fileSystem.setMetadata(map);
        }
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileSize(multipartFile.getSize());
        fileSystem.setFileType(multipartFile.getContentType());
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }

    // 1.上传文件到fastdfs中，返回文件id
    private String fdfs_upload(MultipartFile file) {
        try {
            // 加载fastDfs配置
            this.initFdfsConfig();
            // 创建tracker client
            TrackerClient trackerClient = new TrackerClient();
            // 获取tracker server
            TrackerServer trackerServer = trackerClient.getConnection();
            // 获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            // 创建storage client
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

            // 上传文件
            // 获取文件字节数据
            byte[] bytes = file.getBytes();
            // 获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            // 获取文件扩展名（从最后一个.后一位索引开始截取）
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 文件id
            String fileId = storageClient1.upload_file1(bytes, extName, null);
            return fileId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 初始化fastDfs环境
    private void initFdfsConfig() {
        try {
            ClientGlobal.initByTrackers("tracker_servers");
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
            // 初始化出错
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }
    }

}
