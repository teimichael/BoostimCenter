package stu.napls.boostimcenter.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostimcenter.auth.annotation.Auth;
import stu.napls.boostimcenter.core.dictionary.ConversationConst;
import stu.napls.boostimcenter.core.exception.Assert;
import stu.napls.boostimcenter.core.response.Response;
import stu.napls.boostimcenter.model.Conversation;
import stu.napls.boostimcenter.model.User;
import stu.napls.boostimcenter.service.ConversationService;
import stu.napls.boostimcenter.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private ConversationService conversationService;

    @Resource
    private UserService userService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberList", value = "A set of UUIDs split by ','", example = "UUID0,UUID1,UUID2,", required = true),
    })
    @PostMapping("/create")
    public Response create(@RequestParam String memberList) {
        Conversation conversation = new Conversation();
        conversation.setUuid(UUID.randomUUID().toString());
        conversation.setType(ConversationConst.TYPE_GROUP);
        conversation.setUsers(memberList);
        return Response.success(conversationService.update(conversation));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @PostMapping("/join/{groupUuid}")
    public Response join(@PathVariable("groupUuid") String groupUuid, @ApiIgnore HttpSession session) {
        Conversation conversation = conversationService.findByUuidAndType(groupUuid, ConversationConst.TYPE_GROUP);
        Assert.notNull(conversation, "Group does not exist.");
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "User does not exist.");
        Assert.isTrue(!conversation.getUsers().contains(user.getUuid()), "User has joined this group.");

        conversation.setUsers(conversation.getUsers() + user.getUuid() + ",");
        return Response.success("Join successfully.", conversationService.update(conversation));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @PostMapping("/leave/{groupUuid}")
    public Response leave(@PathVariable("groupUuid") String groupUuid, @ApiIgnore HttpSession session) {
        Conversation conversation = conversationService.findByUuidAndType(groupUuid, ConversationConst.TYPE_GROUP);
        Assert.notNull(conversation, "Group does not exist.");
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "User does not exist.");
        Assert.isTrue(conversation.getUsers().contains(user.getUuid()), "User is not in this group.");
        conversation.setUsers(conversation.getUsers().replace(user.getUuid() + ",", ""));
        return Response.success("Leave successfully.", conversationService.update(conversation));
    }

    @GetMapping("/get/{groupUuid}")
    public Response get(@PathVariable("groupUuid") String groupUuid) {
        Conversation conversation = conversationService.findByUuidAndType(groupUuid, ConversationConst.TYPE_GROUP);
        Assert.notNull(conversation, "Group does not exist.");
        return Response.success(conversation);
    }

}
