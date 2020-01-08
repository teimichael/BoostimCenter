package stu.napls.boostim.auth.request;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import stu.napls.boostim.auth.model.*;
import stu.napls.boostim.config.property.AuthServer;

import javax.annotation.Resource;

/**
 * @Author Tei Michael
 * @Date 12/31/2019
 */
@Component
public class AuthRequest {

    @Resource
    private AuthServer authServer;

    private static final String LOGIN = "/auth/login";
    private static final String LOGOUT = "/auth/logout";
    private static final String PREREGISTER = "/auth/preregister";
    private static final String REGISTER = "/auth/register";
    private static final String VERIFY = "/verify";

    private RestTemplate restTemplate = new RestTemplate();

    public AuthResponse login(AuthLogin authLogin) {
        return restTemplate.postForObject(authServer.getUrl() + LOGIN, authLogin, AuthResponse.class);
    }

    public AuthResponse preregister(AuthPreregister authPreregister) {
        return restTemplate.postForObject(authServer.getUrl() + PREREGISTER, authPreregister, AuthResponse.class);
    }

    public AuthResponse register(AuthRegister authRegister) {
        return restTemplate.postForObject(authServer.getUrl() + REGISTER, authRegister, AuthResponse.class);
    }

    public AuthResponse logout(AuthLogout authLogout) {
        return restTemplate.postForObject(authServer.getUrl() + LOGOUT, authLogout, AuthResponse.class);
    }

    public AuthResponse verify(AuthVerify authVerify) {
        return restTemplate.postForObject(authServer.getUrl() + VERIFY, authVerify, AuthResponse.class);
    }

}
