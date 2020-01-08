package stu.napls.boostim.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stu.napls.boostim.core.dictionary.ConversationConst;
import stu.napls.boostim.core.response.Response;
import stu.napls.boostim.model.Conversation;
import stu.napls.boostim.service.ConversationService;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private ConversationService conversationService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberList", value = "A set of UUIDs split by ','", example = "UUID0,UUID1,UUID2", required = true),
    })
    @PostMapping("/create")
    private Response create(@RequestParam String memberList) {
        Conversation conversation = new Conversation();
        conversation.setUuid(UUID.randomUUID().toString());
        conversation.setType(ConversationConst.TYPE_GROUP);
        conversation.setUsers(memberList);
        return Response.success(conversationService.update(conversation));
    }
}
