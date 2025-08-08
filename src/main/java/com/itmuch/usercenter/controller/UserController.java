package com.itmuch.usercenter.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.itmuch.usercenter.domain.dto.user.JwtTokenRespDTO;
import com.itmuch.usercenter.domain.dto.user.LoginRespDTO;
import com.itmuch.usercenter.domain.dto.user.UserLoginDTO;
import com.itmuch.usercenter.domain.dto.user.UserRespDTO;
import com.itmuch.usercenter.domain.entity.user.User;
import com.itmuch.usercenter.service.UserService;
import com.itmuch.usercenter.util.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {

    private final UserService userService;

    private final WxMaService wxMaService;

    private final JwtOperator jwtOperator;

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Integer id) {
        log.info("我被请求了。。。");
        return userService.getUserById(id);
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        // 微信小程序校验服务端登录的结果
        WxMaJscode2SessionResult result = this.wxMaService.getUserService()
                .getSessionInfo(loginDTO.getCode());

        // 微信的openId
        String openId = result.getOpenid();

        // 看用户是否注册，如果没有注册，就新增；已经注册
        User user = this.userService.login(loginDTO, openId);

        // 颁发TOKEN
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());

        String token = jwtOperator.generateToken(userInfo);

        log.info(
            "用户{}登录成功，生成的token={}，有效期到：{}",
                loginDTO.getWxNickname(),
                token,
                jwtOperator.getExpirationTime()
        );


        // 构建响应
        return  LoginRespDTO.builder()
                .user(UserRespDTO.builder()
                        .id(user.getId())
                        .wxNickname(user.getWxNickname())
                        .avatarUrl(user.getAvatarUrl())
                        .bonus(user.getBonus())
                        .build())
                .token(JwtTokenRespDTO.builder()
                        .expiredTime(jwtOperator.getExpirationTime().getTime())
                        .token(token)
                        .build())
                .build();
    }

}
