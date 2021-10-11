package com.homework.comments.controller.abstractTest;

import com.homework.comments.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public abstract class MockMvcTest {
  @Autowired
  private MockMvc mockMvc;

  protected void mvcPerformPost(String resultJson, String url, String contentJson) throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.request(HttpMethod.POST, url)
                    // 设置返回值类型为json utf-8，否则默认为ISO-8859-1
                    .accept(Constant.APPLICATION_JSON_CHARSET_UTF_8)
                    .contentType(Constant.APPLICATION_JSON_CHARSET_UTF_8).content(contentJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                    .string(containsString(resultJson)))
            .andDo(print());
  }

  protected void mvcPerformGet(String resultJson, String url, String contentJson) throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.request(HttpMethod.GET, url)
                    // 设置返回值类型为json utf-8，否则默认为ISO-8859-1
                    .accept(Constant.APPLICATION_JSON_CHARSET_UTF_8)
                    .contentType(Constant.APPLICATION_JSON_CHARSET_UTF_8).content(contentJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                    .string(containsString(resultJson)))
            .andDo(print());
  }

  protected void mvcPerformGet(String resultJson, String url) throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.request(HttpMethod.GET, url)
                    // 设置返回值类型为json utf-8，否则默认为ISO-8859-1
                    .accept(Constant.APPLICATION_JSON_CHARSET_UTF_8)
                    .contentType(Constant.APPLICATION_JSON_CHARSET_UTF_8))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                    .string(containsString(resultJson)))
            .andDo(print());
  }
}
