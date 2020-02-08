package stu.napls.boostimcenter.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostimcenter.auth.annotation.Auth;
import stu.napls.boostimcenter.core.exception.Assert;
import stu.napls.boostimcenter.core.response.Response;
import stu.napls.boostimcenter.model.Conversation;
import stu.napls.boostimcenter.model.User;
import stu.napls.boostimcenter.service.ConversationService;
import stu.napls.boostimcenter.service.UserService;
import stu.napls.boostimcenter.util.ChatUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Resource
    private ConversationService conversationService;

    @Resource
    private UserService userService;

    @ApiOperation("Get conversation list of user.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @GetMapping("/get/list/{userUuid}")
    private Response getListByUser(@ApiIgnore HttpSession session) {
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "User does not exist.");
        List<Conversation> conversations = conversationService.findByUserUuid(user.getUuid());
        return Response.success(conversations);
    }

    @ApiOperation("Clear unread records.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @PostMapping("/clear/unread")
    private Response clearUnread(@RequestParam String conversationUuid, @ApiIgnore HttpSession session) {
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "User does not exist.");
        user.setUnreadList(ChatUtil.getNewUnreadList(user.getUnreadList(), conversationUuid, ChatUtil.UNREAD_CLEAR));
        userService.update(user);
        return Response.success("Clear successfully.");
    }

}
