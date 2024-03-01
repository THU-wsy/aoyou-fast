package com.thuwsy.aoyou.common.exception;

import com.thuwsy.aoyou.common.constants.HttpStatus;
import com.thuwsy.aoyou.common.response.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.thuwsy.aoyou.common.exception
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/28 18:59
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理项目的自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    public CommonResult serviceExceptionHandler(ServiceException e) {
        log.error("【service异常】{}", e.getMessage());
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandler(Exception e) {
        log.error("【服务器异常】{}", e.getMessage());
        return CommonResult.error(HttpStatus.ERROR, "操作异常");
    }
}
