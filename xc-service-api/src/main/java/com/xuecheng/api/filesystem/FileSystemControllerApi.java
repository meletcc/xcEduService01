package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "文件管理接口", description = "文件管理接口，提供文件的增、删、改、查")
public interface FileSystemControllerApi {

    /**
     * 上传文件
     *
     * @param multipartFile springMvc的文件本身
     * @param fileTag       业务标签
     * @param businessKey   业务key
     * @param metadata      文件元信息
     * @return
     */
    @ApiOperation("上传文件接口")
    UploadFileResult upload(MultipartFile multipartFile,
                            String fileTag,
                            String businessKey,
                            String metadata);
}
