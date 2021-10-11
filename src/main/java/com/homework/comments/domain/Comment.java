package com.homework.comments.domain;

import com.homework.comments.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentId;
    private int userId;
    private String userName;

    @NotNull(message = Constant.COMMENT_WRONG_LENGTH)
    @Length(min = 3, max = 200, message = "留言在3～200个字符间")
    private String content;
    private Integer commentPid;
    private Integer replyId;
    private String replyName;
    private String createTime;

    private Collection<Comment> children;
}
