package com.homework.comments.controller;

import com.homework.comments.controller.abstractTest.RestTempleTest;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


/**
 * 该类重点测试用户登陆，session和cookie交互信息是否正确
 */
@DisplayName("用户模块_controller层测试_TestRestTemplate方法")
class UserControllerRestTempleTest extends RestTempleTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

//    @AfterEach
//    void tearDown() {
//        jdbcTemplate.execute("delete from t_user where user_Name='"+userName+"'");
//    }


    @Test
    @DisplayName("正常登录成功，httpStatus返回200，无token，有返回cookie")
    void userLoginSuccess_noToken() {
        String loginJson = "{\"userName\":\"123@qq.com\",\"userPassword\":\"Aa12345!\"}";

        final ResponseEntity<Response> userResponseEntity = templatePost(loginJson,Constant.LOGIN_URL);
        assertAll(
                ()->assertEquals(HttpStatus.OK,userResponseEntity.getStatusCode()),
                ()->assertEquals(Constant.STATUS_OK,userResponseEntity.getBody().getStatus()),
                ()->assertTrue(userResponseEntity.getHeaders().containsKey("Set-Cookie"))
        );
    }

    @Test
    @DisplayName("正常登录成功，httpStatus返回200，有token返回")
    void userLoginSuccess_token() {
        String loginJson = "{\"userName\":\"123@qq.com\",\"userPassword\":\"Aa12345!\",\"rememberMe\":\"1\"}";

        final ResponseEntity<Response> userResponseEntity = templatePost(loginJson,Constant.LOGIN_URL);
        assertAll(
                ()->assertEquals(HttpStatus.OK,userResponseEntity.getStatusCode()),
                ()->assertEquals(Constant.STATUS_OK,userResponseEntity.getBody().getStatus()),
                ()->assertTrue(userResponseEntity.getBody().getContent().toString().contains("token="))
        );
    }

}