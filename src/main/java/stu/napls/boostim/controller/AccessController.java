package stu.napls.boostim.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostim.auth.annotation.Auth;
import stu.napls.boostim.auth.model.*;
import stu.napls.boostim.auth.request.AuthRequest;
import stu.napls.boostim.core.dictionary.ResponseCode;
import stu.napls.boostim.core.dictionary.StatusCode;
import stu.napls.boostim.core.exception.Assert;
import stu.napls.boostim.core.response.Response;
import stu.napls.boostim.model.Node;
import stu.napls.boostim.model.User;
import stu.napls.boostim.model.vo.RegisterVO;
import stu.napls.boostim.service.NodeService;
import stu.napls.boostim.service.UserService;

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
