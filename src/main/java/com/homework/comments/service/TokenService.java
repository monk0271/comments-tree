package com.homework.comments.service;

import com.homework.comments.domain.User;
import org.springframework.stereotype.Service;


@Service
public interface TokenService {
    public String getToken(User user);
}
