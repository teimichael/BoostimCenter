package stu.napls.boostimcenter.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostimcenter.auth.annotation.Auth;
import stu.napls.boostimcenter.core.exception.Assert;
import stu.napls.boostimcenter.core.response.Response;
import stu.napls.boostimcenter.model.Conversation;
import stu.napls.boostimcenter.model.User;
import stu.napls.boostimcenter.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author Tei Michael
 * @Date 2/8/2020
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @GetMapping("/get/info")
    public Response getUserInfo(@ApiIgnore HttpSession session) {
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "User does not exist.");
        return Response.success(user);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @PostMapping("/update/avatar")
    public Response updateAvatar(String avatar, @ApiIgnore HttpSession session) {
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "User does not exist.");
        user.setAvatar(avatar);
        return Response.success(userService.update(user));
    }
}
