package stu.napls.boostim.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import stu.napls.boostim.auth.annotation.Auth;
import stu.napls.boostim.core.exception.Assert;
import stu.napls.boostim.core.response.Response;
import stu.napls.boostim.model.Node;
import stu.napls.boostim.model.User;
import stu.napls.boostim.service.NodeService;
import stu.napls.boostim.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/node")
public class NodeController {

    @Resource
    private NodeService nodeService;

    @Resource
    private UserService userService;

    @ApiOperation("Return the address of the distributed node.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @GetMapping("/get/best")
    public Response getBest(@ApiIgnore HttpSession session) {
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "Authentication failed.");
        return Response.success("ok", nodeService.findBestNode());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Auth
    @PostMapping("/connect/{nodeId}/{sessionId}")
    public Response connect(@PathVariable("nodeId") Long nodeId, @PathVariable("sessionId") String sessionId, @ApiIgnore HttpSession session) {
        User user = userService.findUserByUuid(session.getAttribute("uuid").toString());
        Assert.notNull(user, "Authentication failed.");

        Node node = nodeService.findById(nodeId);
        Assert.notNull(node, "Node does not exist.");
        node.setClientNumber(node.getClientNumber() + 1);
        nodeService.update(node);

        user.setNode(node);
        user.setSessionId(sessionId);
        userService.update(user);
        return Response.success(user);
    }

    /**
     * TODO Administrative function
     * @param address
     * @return
     */
    @PostMapping("/create")
    public Response create(@RequestParam String address) {
        Node node = new Node();
        node.setAddress(address);
        return Response.success(nodeService.update(node));
    }


}
