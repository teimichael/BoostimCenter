package stu.napls.boostim.model.vo;

import lombok.Data;
import stu.napls.boostim.auth.model.AuthLogin;

@Data
public class LoginVO {

    private String sessionId;

    private AuthLogin authLogin;
}
