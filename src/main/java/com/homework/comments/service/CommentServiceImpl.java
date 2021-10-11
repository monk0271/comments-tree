package com.homework.comments.service;

import com.alibaba.fastjson.JSON;
import com.homework.comments.domain.Comment;
import com.homework.comments.domain.User;
import com.homework.comments.mapper.CommentMapper;
import com.homework.comments.utils.CommonTools;
import com.homework.comments.utils.Constant;
import com.homework.comments.utils.Response;
import com.homework.comments.utils.TreeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public Response getComments() {
        List<Comment> comments = commentMapper.findAll();
        Collection<Comment> tree = new TreeGenerator().createTree(comments);
        return new Response(Constant.STATUS_OK, JSON.toJSONString(tree));
    }

    @Override
    public Response leaveComment(User user, Comment comment) {
        Integer commentPid = comment.getCommentPid();

        comment.setUserId(user.getUserId());
        comment.setUserName(user.getUserName());
        comment.setCommentPid(commentPid==null?0:commentPid);
        comment.setCreateTime(CommonTools.getCurrentTime().toString());
        commentMapper.save(comment);
        return new Response(Constant.STATUS_OK, Constant.COMMENT_SUCCESS);
    }

}
