package com.homework.comments.controller;

import com.homework.comments.annotation.UserLoginToken;
import com.homework.comments.domain.Comment;
import com.homework.comments.domain.User;
import com.homework.comments.service.CommentService;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 发表评论
     */
    @UserLoginToken
    @PostMapping(value = Constant.LEAVE_COMMENT_URL, produces = Constant.APPLICATION_JSON_CHARSET_UTF_8)
    public Response leaveComment(HttpServletRequest request, @RequestBody @Valid Comment comment){
        User user = (User) request.getSession().getAttribute("user");
        return commentService.leaveComment(user,comment);
    }

    @GetMapping(value = Constant.SHOW_COMMENTS)
    public Response showComments(){
        return commentService.getComments();
    }
}
