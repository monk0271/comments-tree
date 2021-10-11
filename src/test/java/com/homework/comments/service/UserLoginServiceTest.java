package com.homework.comments.service;

import com.alibaba.fastjson.JSONObject;
import com.homework.comments.dto.UserLoginDTO;
import com.homework.comments.domain.User;
import com.homework.comments.mapper.UserMapper;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * 用户登陆模块服务层测试，只实例化UserService，通过mock模拟其他依赖
 */
@SpringBootTest(classes = UserService.class)
@DisplayName("用户登录模块_Service层测试_Mock方法")
class UserLoginServiceTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserMapper userMapper;
    @Mock
    TokenService tokenService;

    private UserLoginDTO userLoginDTO;
    private User user;
    private String token;
    private String rememberMe="1";


    @BeforeEach
    void setUp() {
        //初始化测试参数
        String userName = "test01";
        String userPassword = "Aa12345!";
        userLoginDTO = new UserLoginDTO(userName,userPassword,"1");
        user = new User();
        user.setUserName(userName);
        user.setUserPassword(DigestUtils.sha1Hex(userPassword));
        token = "99999999";

        //模拟数据库返回user对象；模拟获取token
        MockitoAnnotations.openMocks(this);

        when(userMapper.findByNameOrEmail(anyString())).thenReturn(user);
        when(tokenService.getToken(user)).thenReturn(token);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("mock数据库操作和token操作。正常登录成功，返回匹配的user对象")
    void userLogin() {
        final Response response = userServiceImpl.userLogin(userLoginDTO);
        JSONObject jsonObject = (JSONObject)response.getContent();

        //正确调用用户名查询数据，正确返回成功登录状态，正确返回user对象
        assertAll(
                ()->verify(userMapper).findByNameOrEmail(user.getUserName()),
                ()->assertEquals(Constant.STATUS_OK,response.getStatus()),
                ()->assertEquals(user, jsonObject.get("user"))
        );
    }

    @Test
    @DisplayName("测试rememberMe功能，能够返回token； 能正常登录")
    void rememberMe() {
        userLoginDTO.setRememberMe(rememberMe);

        final Response response = userServiceImpl.userLogin(userLoginDTO);
        JSONObject jsonObject = (JSONObject)response.getContent();

        //正确调用获取token方法，正确返回token，正常登录
        assertAll(
                ()->verify(tokenService).getToken(user),
                ()->assertEquals(token,jsonObject.get("token")),
                ()->assertEquals(Constant.STATUS_OK,response.getStatus()),
                ()->assertEquals(user, jsonObject.get("user"))
        );
    }

    @Test
    @DisplayName("不使用rememberMe不返回token; 能正常登录")
    void doNotRememberMe() {
        userLoginDTO.setRememberMe(null);

        final Response response = userServiceImpl.userLogin(userLoginDTO);
        JSONObject jsonObject = (JSONObject)response.getContent();

        //不调用token获取方法，不返回token，正常登录
        assertAll(
                ()->verifyNoInteractions(tokenService),
                ()->assertEquals(null,jsonObject.get("token")),
                ()->assertEquals(Constant.STATUS_OK,response.getStatus()),
                ()->assertEquals(user, jsonObject.get("user"))
        );
    }
}