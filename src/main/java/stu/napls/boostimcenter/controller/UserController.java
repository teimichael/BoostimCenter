package stu.napls.boostimcenter.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @ApiOperation("Get conversation list of user.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @GetMapping("/get/info")
    private Response getUserInfo(@ApiIgnore HttpSession session) {
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "User does not exist.");
        return Response.success(user);
    }

}
