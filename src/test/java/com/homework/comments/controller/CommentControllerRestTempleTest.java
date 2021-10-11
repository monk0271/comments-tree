package com.homework.comments.controller;

import com.homework.comments.controller.abstractTest.WithLoginStatusTest;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("该类重点测试留言功能")
class CommentControllerRestTempleTest extends WithLoginStatusTest {

  @DisplayName("通过数据文件批量测试留言功能")
  @ParameterizedTest
  @CsvFileSource(resources = "/test/Success_Comments.csv")
  void leaveMessage(String message){

    final ResponseEntity<Response> userResponseEntity = templatePost(message,Constant.LEAVE_COMMENT_URL,super.getCookie());
    assertAll(
            ()->assertEquals(HttpStatus.OK,userResponseEntity.getStatusCode()),
            ()->assertEquals(Constant.STATUS_OK,userResponseEntity.getBody().getStatus()),
            ()->assertTrue(userResponseEntity.getBody().getContent().toString().startsWith(Constant.COMMENT_SUCCESS))
    );
  }

}
