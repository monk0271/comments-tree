package com.homework.comments.controller;

import com.homework.comments.controller.abstractTest.MockMvcTest;
import com.homework.comments.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户登陆模块控制层测试，用AutoConfigureMockMvc自动注入依赖的springbean，
 * 该模式需要启动整个Spring，会产生事务，需要@Transactional取消事务。
 *
 * 该类重点测试数据库交互
 */
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@DisplayName("用户模块_controller层测试_AutoConfigureMockMvc方法")
class UserControllerMockMvcTest extends MockMvcTest {
    private String loginJson;
    private String registerJson;
    private String userName;
    private String userPassword;
    private String email;

    @BeforeEach
    void setUp() {
        userName="testRegisterUserName";
        userPassword="Aa12345!";
        email="1234@qq.com";
    }


    @Test
    @DisplayName("正常注册成功")
    void userRegisterSuccess() throws Exception{
        registerJson="{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"email\":\""+email+"\"}";
        String resultJson = "{\"status\":\""+Constant.STATUS_OK+"\",\"content\":\"" + Constant.REGISTER_SUCCESS + "\"}";
        mvcPerformPost(resultJson, Constant.USER_REGISTER_URL, registerJson);
    }



    @Test
    @DisplayName("正常登录成功")
    void LoginSuccess() throws Exception {
        userName = "test01";
        loginJson = "{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\"}";
        String resultJson = "{\"status\":\""+Constant.STATUS_OK+"\",\"content\":{\"user\":{\"userId\":21,\"userName\":\"" + userName + "\",\"userPassword\":\"\",\"email\":\"123@qq.com\",\"createTime\":\"2021-09-23 19:41:39.641\"}}}";
        mvcPerformPost(resultJson, Constant.LOGIN_URL, loginJson);

    }

    @Test
    @DisplayName("正常登录成功，rememberMe")
    void userLoginSuccess_token() throws Exception {
        userName = "test01";
        loginJson = "{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"rememberMe\":\"1\"}";
        String resultJson = "{\"status\":\""+Constant.STATUS_OK+"\",\"content\":{\"user\":{\"userId\":21,\"userName\":\"" + userName + "\",\"userPassword\":\"\",\"email\":\"123@qq.com\",\"createTime\":\"2021-09-23 19:41:39.641\"},\"token\":\"";
        mvcPerformPost(resultJson, Constant.LOGIN_URL, loginJson);
    }

    @Test
    @DisplayName("登录失败，密码不正确 ，返回消息正确")
    void userLoginFail_WrongPWD() throws Exception  {
        userName = "test01";
        userPassword="xxx";
        loginJson = "{\"userName\":\"" + userName + "\",\"userPassword\":\""+userPassword+"\",\"rememberMe\":\"1\"}";
        String resultJson = "{\"status\":\""+Constant.STATUS_PASSWORD_WRONG+"\",\"content\":\"" + Constant.PASSWORD_WRONG + "\"}";
        mvcPerformPost(resultJson, Constant.LOGIN_URL, loginJson);
    }
}
