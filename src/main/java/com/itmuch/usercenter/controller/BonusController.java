package com.itmuch.usercenter.controller;

import com.itmuch.usercenter.domain.dto.user.UserAddBonusDTO;
import com.itmuch.usercenter.domain.entity.user.User;
import com.itmuch.usercenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BonusController {

    private final UserService userService;

    @PutMapping("/add-bonus")
    public User addBonus(@RequestBody UserAddBonusDTO userAddBonusDTO) {
        this.userService.addBonus(userAddBonusDTO);

        User user = this.userService.getUserById(userAddBonusDTO.getUserId());

        return user;
    }

}
