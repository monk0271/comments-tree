package com.homework.comments.controller;


import com.homework.comments.controller.abstractTest.MockMvcTest;
import com.homework.comments.utils.Constant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

/**
 * 留言模块控制层测试，用AutoConfigureMockMvc自动注入依赖的springbean，
 * 该模式需要启动整个Spring，会产生事务，需要@Transactional取消事务。
 *
 * 该类重点测试涉及数据库交互功能
 */
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@DisplayName("留言模块_controller层测试_AutoConfigureMockMvc方法")
class CommentControllerMockMvcTest extends MockMvcTest {


  private String leaveUrl = "/comment";
  private String getCommentsUrl = "/comments";


  @Test
  @DisplayName("成功获取留言树,在指定时间内完成测试")
  void getCommentSuccess() throws Exception {
    String resultJson = "{\"status\":\"0\",\"content\":\"[{";

    assertTimeout(Duration.ofSeconds(1), () -> {

      mvcPerformGet(resultJson, getCommentsUrl);
    });
  }


}
