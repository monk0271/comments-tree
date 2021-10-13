package com.homework.comments.mapper;


import com.homework.comments.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {


    /**
     * @param userId
     * @return
     */
    User findById(int userId);

    /**
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * @param user
     * @return
     */
    int save(User user);

    User findByEmail(String email);

    User findByNameOrEmail(String userName);
}
