package com.advice.exception;

import com.dreamsecurity.exception.AbstractRequestException;
import com.dreamsecurity.exception.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice
public class ExceptionAdvice {
//    @ExceptionHandler(AbstractRequestException.class)
//    public Object handleRequestExceptionMvc(AbstractRequestException e, ServletWebRequest request) {
//        log.info("remoteAddr: {} path: {}", request.getRequest().getRemoteAddr(), request.getRequest().getServletPath());
//        return CommonResult.error(e.getHttpStatus().value(), e.getMessage());
//    }

    @ExceptionHandler(AbstractRequestException.class)
    public Object handleRequestException(AbstractRequestException e) {
        return CommonResult.error(e.getHttpStatus().value(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handle(Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Object handleNoHandlerFoundException(Exception e) {
//        e.printStackTrace();
//        return CommonResult.error(HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleNotSupportedMethodException(Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingRequestParameterException(Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public Object handleServletRequestBindingException(Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST.value(), "Missing Attribute");
    }
}
