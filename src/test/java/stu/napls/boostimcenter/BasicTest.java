package stu.napls.boostimcenter;

import org.junit.jupiter.api.Test;
import stu.napls.boostimcenter.util.ChatUtil;

public class BasicTest {

    @Test
    public void testUnreadList() {
//        String record = "d8a12a1f-632e-4cc1-81bd-1558c77ffcc1";
//        String records = "d8a12a1f-632e-4cc1-81bd-1558c77ffcc3:123123,d8a12a1f-632e-4cc1-81bd-1558c77ffcc1:1313";
        String list = ChatUtil.getNewUnreadList("d8a12a1f-632e-4cc1-81bd-1558c77ffcc2:1,d8a12a1f-632e-4cc1-81bd-1558c77ffcc1:1,d8a12a1f-632e-4cc1-81bd-1558c77ffcc3:3", "d8a12a1f-632e-4cc1-81bd-1558c77ffcc3", ChatUtil.UNREAD_CLEAR);
        System.out.println(list);
    }

    @Test
    public void testGroup() {
        String list = "d8a12a1f-632e-4cc1-81bd-1558c77ffcc2,d8a12a1f-632e-4cc1-81bd-1558c77ffcc1,d8a12a1f-632e-4cc1-81bd-1558c77ffcc0,";
        String[] array = list.split(",");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        list = list.replace("d8a12a1f-632e-4cc1-81bd-1558c77ffcc1,", "");
        System.out.println(list);

    }

}
