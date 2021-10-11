package com.homework.comments.controller;

import com.alibaba.fastjson.JSONObject;
import com.homework.comments.controller.abstractTest.MockMvcTest;
import com.homework.comments.mapper.UserMapper;
import com.homework.comments.service.UserService;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


/**
 * 用户登陆模块控制层测试，只实例化UserController，通过MockBean模拟其他依赖
 *
 * 该类重点测试controller参数校验
 */
@WebMvcTest(controllers = UserController.class)
@DisplayName("用户模块_controller层测试_WebMvcTest方法")
class UserControllerWebMvcTest extends MockMvcTest {
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper mapper;

    private String loginUrl;
    private String registerUrl;
    private String resultJson = "{\"status\":\"0\",\"content\":{}}";
    private String userName;
    private String userPassword;
    private String email;

    @BeforeEach
    void setUp() {
        loginUrl="/userLogin";
        registerUrl="/userRegister";
        userName="testRegisterUserName";
        userPassword="Aa12345!";
        email="1234@qq.com";
        resultJson = "{\"status\":\"0\",\"content\":{}}";

        when(userService.userLogin(any())).thenReturn(new Response("0",new JSONObject()));
        when(userService.userRegister(any())).thenReturn(new Response("0",new JSONObject()));
    }


    @Test
    @DisplayName("正常注册成功，httpStatus返回200")
    void userRegisterSuccess() throws Exception{
        String registerJson="{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"email\":\""+email+"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);
    }

    @Test
    @DisplayName("注册失败，用户名不规范")
    void registerFail_WrongUserName() throws Exception{
        userName = "123";
        String registerJson="{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"email\":\""+email+"\"}";
        resultJson = "{\"status\":\""+Constant.STATUS_VALIDATE_FAILURE+"\",\"content\":\""+Constant.USERNAME_WRONG_LENGTH+"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);

        userName = "/////";
        registerJson="{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"email\":\""+email+"\"}";
        resultJson = "{\"status\":\""+Constant.STATUS_VALIDATE_FAILURE+"\",\"content\":\"" + Constant.USERNAME_WRONG_FORMAT  +"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);
        //userName=null
        registerJson="{\"userPassword\":\""+userPassword+"\",\"email\":\""+email+"\"}";
        resultJson = "{\"status\":\""+Constant.STATUS_VALIDATE_FAILURE+"\",\"content\":\""+ Constant.USERNAME_WRONG_NULL +"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);
    }

    @Test
    @DisplayName("注册失败，email不规范")
    void registerFail_WrongEmail() throws Exception{
        email = "123";
        String registerJson="{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"email\":\""+email+"\"}";
        resultJson = "{\"status\":\""+Constant.STATUS_VALIDATE_FAILURE+"\",\"content\":\""+Constant.EMAIL_WRONG_FORMAT+"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);
        //email=null
        registerJson="{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\"}";
        resultJson = "{\"status\":\""+Constant.STATUS_VALIDATE_FAILURE+"\",\"content\":\""+Constant.EMAIL_WRONG_NULL+"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);
    }

    @Test
    @DisplayName("注册失败，密码不规范")
    void registerFail_WrongPWD() throws Exception{
        userPassword = "Aa123!";
        String registerJson="{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"email\":\""+email+"\"}";
        resultJson = "{\"status\":\""+Constant.STATUS_VALIDATE_FAILURE+"\",\"content\":\""+Constant.PASSWORD_WRONG_LENGTH+"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);

        userPassword = "Aa1234578";
        registerJson="{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"email\":\""+email+"\"}";
        resultJson = "{\"status\":\""+Constant.STATUS_VALIDATE_FAILURE+"\",\"content\":\""+Constant.PASSWORD_WRONG_FORMAT+"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);
        //userPassword=null
        registerJson="{\"userName\":\"" + userName + "\",\"email\":\""+email+"\"}";
        resultJson = "{\"status\":\""+Constant.STATUS_VALIDATE_FAILURE+"\",\"content\":\""+Constant.PASSWORD_WRONG_NULL+"\"}";
        mvcPerformPost(resultJson, registerUrl, registerJson);
    }

    @Test
    @DisplayName("正常登录成功，httpStatus返回200")
    void loginSuccess() throws Exception {
        String loginJson = "{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\"}";
        mvcPerformPost(resultJson, loginUrl, loginJson);
    }

}
