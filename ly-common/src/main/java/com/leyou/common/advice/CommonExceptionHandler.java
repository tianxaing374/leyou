package com.leyou.common.advice;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//默认拦截所有controller
//通用的异常处理
@ControllerAdvice
public class CommonExceptionHandler {

    //利用 状态码 和 失败消息 封装为ExceptionEnum
    //利用ExceptionEnum构造自定义LyException
    //在controller层抛出异常(service层不抛出异常，因为要做回滚)
    //利用 @ControllerAdvice与@ExceptionHandler(Class类)捕获响应的异常
    //在利用捕获的异常获得封装的消息，再封装为返回给前端的信息

    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handlerException(LyException e){
        ExceptionEnum em = e.getExceptionEnum();
        return ResponseEntity.status(em.getHttpStatus()).body(new ExceptionResult(e.getExceptionEnum()));
    }

}
