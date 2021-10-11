package com.homework.comments.utils;

public class Constant {


  private Constant(){}
  //status
  public static final String STATUS_OK = "0";
  public static final String STATUS_FAILURE = "-1";
  public static final String STATUS_VALIDATE_FAILURE = "-11";
  public static final String STATUS_EMAIL_EXISTS = "-12";
  public static final String STATUS_PASSWORD_WRONG = "-13";
  public static final String STATUS_USERNAME_EXISTS = "-14";
  public static final String STATUS_USER_NOT_EXISTS = "-15";
  public static final String STATUS_TOKEN_NOT_EXISTS = "-16";
  public static final String STATUS_TOKEN_INVALID = "-17";
  //HTTP
  public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
  //Validation message
  public static final String SYSTEM_ERROR = "服务器出错";
  public static final String COMMENT_WRONG_LENGTH = "留言在3～200个字符间";
  public static final String REGISTER_SUCCESS = "注册成功！";
  public static final String USERNAME_WRONG_LENGTH = "用户名5～20个字符";
  public static final String USERNAME_WRONG_FORMAT = "用户名只能是数字或字母";
  public static final String USERNAME_WRONG_NULL = "用户名不能为空";
  public static final String EMAIL_WRONG_FORMAT = "邮箱不正确";
  public static final String EMAIL_WRONG_NULL = "邮箱不能为空";
  public static final String PASSWORD_WRONG_LENGTH = "密码8～20个字符";
  public static final String PASSWORD_WRONG_FORMAT = "密码必须含有一个大写一个小写字母，一个数字，一个特殊字符";
  public static final String PASSWORD_WRONG_NULL = "密码不能为空";
  public static final String PASSWORD_WRONG = "密码错误";
  public static final String USER_NOT_EXISTS = "用户不存在";
  public static final String USERNAME_EXISTS = "此用户名已经存在";
  public static final String EMAIL_EXISTS = "此email已经存在";
  public static final String COMMENT_SUCCESS = "留言成功！";
  public static final String TOKEN_NOT_EXISTS = "无token，请重新登录";
  public static final String TOKEN_INVALID = "没有访问权限或token失效";
  //URL
  public static final String LOGIN_URL = "/userLogin";
  public static final String GET_CURRENT_USER_URL = "/user";
  public static final String USER_REGISTER_URL = "/userRegister";
  public static final String LEAVE_COMMENT_URL = "/comment";
  public static final String SHOW_COMMENTS = "/comments";
}
