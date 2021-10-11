package com.homework.comments.controller.abstractTest;

import com.homework.comments.utils.Constant;
import org.junit.jupiter.api.BeforeAll;

public abstract class WithLoginStatusTest extends RestTempleTest{
  private String cookie;

  protected String getCookie(){return cookie;};

  @Override
  @BeforeAll
  protected void init() {
    super.init();
    //    String loginJson = "{\"userName\":\"123@qq.com\",\"userPassword\":\"Aa12345!\",\"rememberMe\":\"1\"}";
    String loginJson = "{\"userName\":\"123@qq.com\",\"userPassword\":\"Aa12345!\"}";
    cookie = templatePost(loginJson, Constant.LOGIN_URL, null).getHeaders().get("Set-Cookie").get(0)
            .split(";")[0];
    System.out.println("返回的cookie:"+cookie);
  }
}
