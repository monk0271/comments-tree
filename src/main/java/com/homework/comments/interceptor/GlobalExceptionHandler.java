package com.homework.comments.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.homework.comments.exception.TokenException;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 方法参数校验
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        //拼接所有错误
        String message = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));

        return new Response(Constant.STATUS_VALIDATE_FAILURE, message);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException e) {
        return new Response(Constant.STATUS_VALIDATE_FAILURE, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(JWTDecodeException.class)
    public Response handleJWTDecodeException(JWTDecodeException e) {
        e.printStackTrace();
        return new Response(Constant.STATUS_FAILURE, Constant.SYSTEM_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(TokenException.class)
    public Response handleTokenException(TokenException e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = Constant.TOKEN_NOT_EXISTS;
        }
        return new Response(Constant.STATUS_TOKEN_NOT_EXISTS, msg);
    }

    @ResponseBody
    @ExceptionHandler(JWTVerificationException.class)
    public Response handleJWTVerificationException(JWTVerificationException e) {
        return new Response(Constant.STATUS_TOKEN_INVALID, Constant.TOKEN_INVALID);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        e.printStackTrace();
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = Constant.SYSTEM_ERROR;
        }
        return new Response(Constant.STATUS_FAILURE,msg);
    }
}
