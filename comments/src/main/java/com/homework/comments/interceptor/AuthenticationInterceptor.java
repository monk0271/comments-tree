package com.homework.comments.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.homework.comments.annotation.UserLoginToken;
import com.homework.comments.domain.User;
import com.homework.comments.exception.TokenException;
import com.homework.comments.mapper.UserMapper;
import com.homework.comments.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * 拦截器，实现preHandle，用于验证请求，流程如下
 * 1.从 http 请求头中取出 token，
 * 2.判断是否映射到方法
 * 4.检查有没有需要用户登录的注解，有则需要取出并验证
 * 5.认证通过则可以访问，不通过会报相关错误信息
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object controller) throws Exception {
        // 如果不是映射到方法直接通过
        if(!(controller instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)controller;
        Method method=handlerMethod.getMethod();
        //检查有没有需要用户登录的注解，有则需要取出并验证
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                //不通过会直接抛出异常
                tokenVerify(httpServletRequest);
            }
        }
        return true;
    }

    private void tokenVerify(HttpServletRequest httpServletRequest) throws JWTVerificationException, TokenException {
        //先判断session
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user==null) {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            // 执行认证
            if (token == null) {
                throw new TokenException();
            }
            // 获取 token 中的 user id
            String userId;

            userId = JWT.decode(token).getAudience().get(0);
            user = userMapper.findById(Integer.parseInt(userId));

            if (user == null) {
                throw new TokenException(Constant.USER_NOT_EXISTS);
            } else {
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
                jwtVerifier.verify(token);
                //放入session中（session有效期，关闭浏览器则退出）
                user.setUserPassword("");
                httpServletRequest.getSession().setAttribute("user", user);
            }
        }
        //else 说明session还在
    }


}
