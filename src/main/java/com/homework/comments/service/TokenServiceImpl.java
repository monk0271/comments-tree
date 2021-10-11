package com.homework.comments.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.homework.comments.domain.User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 使用HS256生成token,密钥则是用户的密码，唯一密钥的话可以保存在服务端。
 * withAudience()存入需要保存在token的信息，这里我把用户ID存入token中
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getToken(User user) {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        return JWT.create()
                .withIssuedAt(date)
                .withExpiresAt(calendar.getTime())// 优化可配置
                .withAudience(user.getUserId()+"")
                .sign(Algorithm.HMAC256(user.getUserPassword()));
    }

}
