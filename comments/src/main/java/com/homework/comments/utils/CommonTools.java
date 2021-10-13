package com.homework.comments.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Timestamp;

public class CommonTools {
    private CommonTools(){}

    public static boolean isEmpty(String str){
        return str == null || str.isEmpty() || str.equals("");
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static Timestamp getCurrentTime(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static boolean verifyPwd(String userShaPassword, String userLoginPassword) {
        return userShaPassword.equals(DigestUtils.sha1Hex(userLoginPassword));
    }

}
