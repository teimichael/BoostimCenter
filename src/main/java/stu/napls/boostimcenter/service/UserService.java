package stu.napls.boostimcenter.service;

import stu.napls.boostimcenter.model.User;

public interface UserService {
    User findUserByUuid(String uuid);

    User findUserBySessionId(String sessionId);

    User update(User user);
}
