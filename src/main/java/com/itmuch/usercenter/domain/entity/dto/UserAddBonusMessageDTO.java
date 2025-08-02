package com.itmuch.usercenter.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAddBonusMessageDTO {

    private Integer userId;

    private Integer bonus;

}

