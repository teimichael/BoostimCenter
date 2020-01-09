package stu.napls.boostim.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostim.core.response.Response;
import stu.napls.boostim.service.MessageService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Resource
    private MessageService messageService;

    @ApiOperation("Get history of the conversation.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "Size of a page", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "Page number starting from 0", dataType = "string", paramType = "query")
    })
    @GetMapping("/get/{conversationUuid}")
    private Response getByConversation(@PathVariable("conversationUuid") String conversationUuid, @ApiIgnore Pageable pageable) {
        return Response.success(messageService.findByConversationUuidOrderByTimestamp(conversationUuid, pageable));
    }

}
