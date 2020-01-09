package stu.napls.boostimcenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import stu.napls.boostimcenter.service.ConversationService;
import stu.napls.boostimcenter.service.UserService;

import javax.annotation.Resource;

@SpringBootTest
class BoostimCenterApplicationTests {

    @Resource
    private ConversationService conversationService;

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {

    }

}
