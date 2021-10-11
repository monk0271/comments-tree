package com.homework.comments.controller;

import com.homework.comments.controller.abstractTest.MockMvcTest;
import com.homework.comments.interceptor.AuthenticationInterceptor;
import com.homework.comments.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * 留言模块控制层测试，只实例化CommentController，通过MockBean模拟其他依赖
 *
 * 该类重点测试参数校验
 */
@WebMvcTest(controllers = CommentController.class)
@DisplayName("留言模块_controller层测试_WebMvcTest方法")
class CommentControllerWebMvcTest extends MockMvcTest {

    @MockBean
    CommentService commentService;

    @MockBean
    AuthenticationInterceptor authenticationInterceptor;

    private String resultJson = "{\"status\":\"0\",\"content\":{}}";

    @BeforeEach
    void setUp() throws Exception {
        when(authenticationInterceptor.preHandle(any(),any(),any())).thenReturn(true);
    }



    @DisplayName("通过cvs提供测试数据，参数化测试错误留言拦截功能")
    @ParameterizedTest
    @CsvFileSource(resources = "/test/Wrong_Comments.csv")
    void leaveComment_wrong(String commentJson,String resultJson) throws Exception {
        String leaveUrl="/comment";
        mvcPerformPost(resultJson, leaveUrl, commentJson);
    }

}