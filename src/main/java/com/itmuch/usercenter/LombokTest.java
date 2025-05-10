package com.itmuch.usercenter;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LombokTest {

    //public static final Logger logger = LoggerFactory.getLogger(LombokTest.class);

    public static void main(String[] args) {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .name("test")
                .password("123456")
                .confirmPassword("123456")
                .mobile("13800000000")
                .agreement(true)
                .build();

        log.info("构建的UserRigsterDTO为：{}", userRegisterDTO);
    }
}

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
class UserRegisterDTO {
    private final String name;

    private final String password;

    private String confirmPassword;

    private String mobile;

    private boolean agreement;

}
