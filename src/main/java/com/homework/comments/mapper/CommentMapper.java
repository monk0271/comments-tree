package com.homework.comments.mapper;

import com.homework.comments.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    List<Comment> findAll();

    int save(Comment comment);

    Comment findById(int commentId);
}
