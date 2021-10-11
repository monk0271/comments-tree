package com.homework.comments.service;

import com.homework.comments.domain.Comment;
import com.homework.comments.domain.User;
import com.homework.comments.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface CommentService {
    Response getComments();
    Response leaveComment(User user, Comment comment);
}
