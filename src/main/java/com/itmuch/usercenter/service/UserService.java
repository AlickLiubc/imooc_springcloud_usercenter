package com.itmuch.usercenter.service;

import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.dto.user.UserLoginDTO;
import com.itmuch.usercenter.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    public User getUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    public User login(UserLoginDTO loginDTO, String openId) {
        User user = this.userMapper.selectOne(
                User.builder()
                    .wxId(openId)
                    .build()
        );

        if (user == null) {
            User userToSave = User.builder()
                                  .wxId(openId)
                                  .bonus(300)
                                  .wxNickname(loginDTO.getWxNickname())
                                  .avatarUrl(loginDTO.getAvatarUrl())
                                  .roles("user")
                                  .createTime(new Date())
                                  .updateTime(new Date())
                                  .build();

            this.userMapper.insertSelective(userToSave);

            return userToSave;
        }

        return user;
    }

}
