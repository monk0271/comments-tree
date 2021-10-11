package com.homework.comments.controller;

import com.alibaba.fastjson.JSONObject;
import com.homework.comments.dto.UserLoginDTO;
import com.homework.comments.annotation.UserLoginToken;
import com.homework.comments.domain.User;
import com.homework.comments.service.UserService;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 注册新用户
     *
     */
    @PostMapping(Constant.USER_REGISTER_URL)
    public Response userRegister(@RequestBody @Valid User user){
        return userService.userRegister(user);
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    @UserLoginToken
    @GetMapping
    public Response pulse(){
        return new Response("0","");
    }

    /**
     * 获取当前会话用户
     *
     * @return
     */
    @UserLoginToken
    @GetMapping(value = Constant.GET_CURRENT_USER_URL)
    public Response getUser(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user",user);//待优化
        return new Response("0",jsonObject);
    }

    /**
     * 用户登录
     *
     */
    @PostMapping(value = Constant.LOGIN_URL, produces = Constant.APPLICATION_JSON_CHARSET_UTF_8)
    public Response userLogin(HttpServletRequest request,@RequestBody UserLoginDTO userLoginDTO){
        Response response = userService.userLogin(userLoginDTO);
        if(response.getStatus().equals("0")){
            JSONObject jsonObject=(JSONObject)response.getContent();
            User user = (User) jsonObject.get("user");
            request.getSession().setAttribute("user", user);
        }
        return response;
    }


}
