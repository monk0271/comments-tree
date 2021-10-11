package com.homework.comments.domain;

import com.homework.comments.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

    private int userId;

    @NotNull(message = Constant.USERNAME_WRONG_NULL)
    @Length(min = 5, max = 20, message = Constant.USERNAME_WRONG_LENGTH)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = Constant.USERNAME_WRONG_FORMAT)
    private String userName;

    @NotNull(message = Constant.PASSWORD_WRONG_NULL)
    @Length(min = 8, max = 20, message = Constant.PASSWORD_WRONG_LENGTH)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = Constant.PASSWORD_WRONG_FORMAT)
    private String userPassword;

    @NotNull(message = Constant.EMAIL_WRONG_NULL)
    @Email(message = Constant.EMAIL_WRONG_FORMAT)
    private String email;

    private String createTime;
}
