package com.itmuch.usercenter.service;

import com.itmuch.usercenter.dao.bonus.BonusEventLogMapper;
import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.dto.user.UserAddBonusDTO;
import com.itmuch.usercenter.domain.dto.user.UserLoginDTO;
import com.itmuch.usercenter.domain.entity.bonus.BonusEventLog;
import com.itmuch.usercenter.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private BonusEventLogMapper bonusEventLogMapper;

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

    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusDTO userAddBonusDTO) {
        Integer userId = userAddBonusDTO.getUserId();
        Integer bonus = userAddBonusDTO.getBonus();

        // 查询用户积分
        User user = this.userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + bonus);

        // 修改积分
        this.userMapper.updateByPrimaryKeySelective(user);

        // 添加bonus_event_log表的数据
        this.bonusEventLogMapper.insert(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event(userAddBonusDTO.getEvent())
                        .description(userAddBonusDTO.getDescription())
                        .createTime(new Date())
                        .build()
        );

    }
}
