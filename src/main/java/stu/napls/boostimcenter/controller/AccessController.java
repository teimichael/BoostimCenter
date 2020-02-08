package stu.napls.boostimcenter.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostimcenter.auth.annotation.Auth;
import stu.napls.boostimcenter.auth.model.*;
import stu.napls.boostimcenter.auth.request.AuthRequest;
import stu.napls.boostimcenter.core.dictionary.ResponseCode;
import stu.napls.boostimcenter.core.dictionary.StatusCode;
import stu.napls.boostimcenter.core.exception.Assert;
import stu.napls.boostimcenter.core.response.Response;
import stu.napls.boostimcenter.model.User;
import stu.napls.boostimcenter.model.vo.RegisterVO;
import stu.napls.boostimcenter.service.NodeService;
import stu.napls.boostimcenter.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/access")
public class AccessController {

    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);

    @Resource
    private AuthRequest authRequest;

    @Resource
    private UserService userService;

    @Resource
    private NodeService nodeService;

    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestBody AuthLogin authLogin) {
        AuthResponse authResponse = authRequest.login(authLogin);

        Assert.notNull(authResponse, "Authentication failed.");
        Assert.isTrue(authResponse.getCode() == ResponseCode.SUCCESS, authResponse.getMessage());

        return Response.success("Login successfully.", authResponse.getData());
    }

    @PostMapping("/register")
    @ResponseBody
    public Response register(@RequestBody RegisterVO registerVO) {
        AuthPreregister authPreregister = new AuthPreregister();
        authPreregister.setUsername(registerVO.getUsername());
        authPreregister.setPassword(registerVO.getPassword());
        AuthResponse authResponse = authRequest.preregister(authPreregister);
        Assert.notNull(authResponse, "Preregistering auth server failed.");
        Assert.isTrue(authResponse.getCode() == ResponseCode.SUCCESS, authResponse.getMessage());
        String uuid = authResponse.getData().toString();

        User user = new User();
        user.setUuid(uuid);
        user.setStatus(StatusCode.NORMAL);
        userService.update(user);

        AuthRegister authRegister = new AuthRegister();
        authRegister.setUuid(uuid);
        authResponse = authRequest.register(authRegister);
        Assert.isTrue(authResponse != null && authResponse.getCode() == ResponseCode.SUCCESS, "Register failed. Please contact the administrator.");

        return Response.success("Register successfully");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @PostMapping("/logout")
    @ResponseBody
    public Response logout(@ApiIgnore HttpSession session) {
        // Logout auth
        AuthLogout authLogout = new AuthLogout();
        authLogout.setUuid(session.getAttribute("uuid").toString());
        AuthResponse authResponse = authRequest.logout(authLogout);
        Assert.notNull(authResponse, "Authentication failed.");
        Assert.isTrue(authResponse.getCode() == ResponseCode.SUCCESS, "Logout failed.");

        return Response.success("Logout successfully.");
    }
}
