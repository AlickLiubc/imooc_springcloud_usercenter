package com.itmuch.usercenter.rocketmq;

import com.itmuch.usercenter.dao.bonus.BonusEventLogMapper;
import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.entity.bonus.BonusEventLog;
import com.itmuch.usercenter.domain.entity.dto.UserAddBonusMessageDTO;
import com.itmuch.usercenter.domain.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RocketMQMessageListener(topic = "add-bonus", consumerGroup = "consume-group")
@Slf4j
public class UserAddBonusListener implements RocketMQListener<UserAddBonusMessageDTO> {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMessageDTO userAddBonusMessageDTO) {
        Integer userId = userAddBonusMessageDTO.getUserId();
        Integer bonus = userAddBonusMessageDTO.getBonus();

        // 添加用户积分
        User user = userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + bonus);
        userMapper.updateByPrimaryKey(user);

        // 添加日志操作记录
        BonusEventLog bonusEventLog = new BonusEventLog();
        bonusEventLog.setEvent("CONTRIBUTE");
        bonusEventLog.setUserId(userId);
        bonusEventLog.setValue(bonus);
        bonusEventLog.setDescription("投稿加积分...");
        bonusEventLog.setCreateTime(new Date());

        bonusEventLogMapper.insert(bonusEventLog);
        log.info("添加积分成功。。。");
    }

}
