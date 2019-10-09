package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 * 使用 @ControllerAdvice和 @ExceptionHandler注解来捕获指定类型的异常
 **/
@ControllerAdvice// 控制器增强
public class ExceptionCatch {

    // 记录异常日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    // 定义map，配置不可预知异常类型所对应的错误代码
    // ImmutableMap是一个线程安全，只读不可修改的Map集合
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    // 定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    // 捕获CustomException此类可预知异常
    @ExceptionHandler(CustomException.class)
    // 响应异常信息为json格式
    @ResponseBody
    public ResponseResult customException(CustomException customException) {
        //记录日志
        LOGGER.error("catch exception:{}", customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    // 捕获Exception此类不可预知异常
    // 此类异常一般是第三方组件或框架抛出
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        // 记录日志
        LOGGER.error("catch exception:{}", exception.getMessage());
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();// EXCEPTIONS构建成功
        }
        // 从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        } else {
            // 在map中没有找到，则统一返回99999异常
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 定义请求体为空的不可预知类异常类型所对应的错误代码
     * 需要该构建器去放入map中，先放在builder中，此时map为空，随后才放入map
     */
    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }
}
