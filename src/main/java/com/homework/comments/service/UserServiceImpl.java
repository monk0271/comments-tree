package com.homework.comments.service;

import com.alibaba.fastjson.JSONObject;
import com.homework.comments.dto.UserLoginDTO;
import com.homework.comments.domain.User;
import com.homework.comments.mapper.UserMapper;
import com.homework.comments.utils.CommonTools;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public Response userLogin(UserLoginDTO userLoginDTO) {
        Response re;

        User user = userMapper.findByNameOrEmail(userLoginDTO.getUserName());
        if(user != null){
            if(CommonTools.verifyPwd(user.getUserPassword(),userLoginDTO.getUserPassword())) {
                JSONObject jsonObject=new JSONObject();
                if(CommonTools.isNotEmpty(userLoginDTO.getRememberMe()))
                    jsonObject.put("token",tokenService.getToken(user));
                user.setUserPassword("");
                jsonObject.put("user",user);
                re = new Response(Constant.STATUS_OK, jsonObject);
            }else
                re = new Response(Constant.STATUS_PASSWORD_WRONG, Constant.PASSWORD_WRONG);
        }else
            re = new Response(Constant.STATUS_USER_NOT_EXISTS, Constant.USER_NOT_EXISTS);
        return re;
    }

    @Override
    public Response userRegister(User user) {
        User userExists = userMapper.findByUserName(user.getUserName());
        if(userExists!=null)
            return new Response(Constant.STATUS_USERNAME_EXISTS, Constant.USERNAME_EXISTS);
        userExists = userMapper.findByEmail(user.getEmail());
        if(userExists!=null)
            return new Response(Constant.STATUS_EMAIL_EXISTS, Constant.EMAIL_EXISTS);

        user.setUserPassword(DigestUtils.sha1Hex(user.getUserPassword()));
        user.setCreateTime(CommonTools.getCurrentTime().toString());
        userMapper.save(user);
        user.setUserPassword("");
        return new Response(Constant.STATUS_OK, Constant.REGISTER_SUCCESS);
    }

//
}
