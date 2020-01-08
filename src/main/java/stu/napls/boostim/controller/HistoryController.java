package stu.napls.boostim.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stu.napls.boostim.core.response.Response;
import stu.napls.boostim.service.MessageService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Resource
    private MessageService messageService;

    @ApiOperation("Get history of the conversation.")
    @GetMapping("/get/{conversationUuid}")
    private Response getByConversation(@PathVariable("conversationUuid") String conversationUuid) {
        return Response.success(messageService.findByConversationUuid(conversationUuid));
    }

}
