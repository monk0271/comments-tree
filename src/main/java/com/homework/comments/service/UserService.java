package com.homework.comments.service;

import com.homework.comments.dto.UserLoginDTO;
import com.homework.comments.domain.User;
import com.homework.comments.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface UserService {

    Response userLogin(UserLoginDTO userDTO);

    Response userRegister(User user);

}
