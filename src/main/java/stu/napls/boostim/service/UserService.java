package stu.napls.boostim.service;

import stu.napls.boostim.model.User;

public interface UserService {
    User findUserByUuid(String uuid);

    User findUserBySessionId(String sessionId);

    User update(User user);
}
