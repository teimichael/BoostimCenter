package stu.napls.boostimcenter.util;

import stu.napls.boostimcenter.core.dictionary.MessageConst;

public class ChatUtil {

    public static final int UNREAD_CLEAR = 0;
    public static final int UNREAD_ADD = 1;

    public static String getNewUnreadList(String unreadList, String conversationUuid, int mode) {
        String newUnreadList;
        switch (mode) {
            case UNREAD_CLEAR:
                newUnreadList = clearUnreadList(unreadList, conversationUuid);
                break;
            default:
                newUnreadList = addUnreadList(unreadList, conversationUuid);
        }

        return newUnreadList;
    }

    private static String addUnreadList(String unreadList, String conversationUuid) {
        StringBuilder result = new StringBuilder();
        boolean isFound = false;
        if (unreadList != null) {
            String[] unreadArray = unreadList.split(MessageConst.RECORD_SPLITTER);
            String[] unreadPair;
            for (int i = 0; i < unreadArray.length; i++) {
                if (unreadArray[i].contains(conversationUuid)) {
                    isFound = true;
                    unreadPair = unreadArray[i].split(MessageConst.KV_SPLITTER);
                    result.append(unreadPair[0]).append(MessageConst.KV_SPLITTER);
                    result.append(Integer.parseInt(unreadPair[1]) + 1);
                } else {
                    result.append(unreadArray[i]);
                }
                result.append(MessageConst.RECORD_SPLITTER);
            }
            if (isFound) {
                result.deleteCharAt(result.length() - 1);
            } else {
                result.append(conversationUuid).append(MessageConst.KV_SPLITTER).append(1);
            }
        } else {
            result.append(conversationUuid).append(MessageConst.KV_SPLITTER).append(1);
        }
        return result.toString();
    }

    private static String clearUnreadList(String unreadList, String conversationUuid) {
        StringBuilder stringBuilder = new StringBuilder();
        if (unreadList != null) {
            String[] unreadArray = unreadList.split(MessageConst.RECORD_SPLITTER);
            for (int i = 0; i < unreadArray.length; i++) {
                if (unreadArray[i].contains(conversationUuid)) {
                    continue;
                } else {
                    stringBuilder.append(unreadArray[i]);
                }
                stringBuilder.append(MessageConst.RECORD_SPLITTER);
            }
            if (stringBuilder.length() > 0) {
                unreadList = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
            } else {
                unreadList = null;
            }
        }
        return unreadList;
    }

}
