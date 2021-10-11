package com.homework.comments.controller.abstractTest;

import com.homework.comments.CommentsApplication;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {CommentsApplication.class})
public abstract class RestTempleTest {
  @LocalServerPort
  private int port;//随机端口号

  private String base;

  //注意TestRestTemplate请求Controller是两个不同进程，无法通过TestRestTemplate回滚controller触发的事务
  private TestRestTemplate template = new TestRestTemplate();

  @BeforeAll
  protected void init(){
    base = String.format("http://127.0.0.1:%d/CommentsTree/",port);
  }



  /**
   * template.postForEntity
   *
   */
  protected ResponseEntity<Response> templatePost(String requestJson, String url, String cookie ) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Cookie",cookie);
    HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

    final ResponseEntity<Response> userResponseEntity = template.postForEntity(base+url, request, Response.class);
    System.out.println("web容器返回信息："+userResponseEntity.toString());
    return userResponseEntity;
  }

  /**
   * template.postForEntity
   *
   */
  protected ResponseEntity<Response> templatePost(String requestJson,String url ) {
    return templatePost(requestJson,url, null);
  }
}
