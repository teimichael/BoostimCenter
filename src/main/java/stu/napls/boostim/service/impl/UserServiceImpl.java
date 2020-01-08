package stu.napls.boostim.service.impl;

import org.springframework.stereotype.Service;
import stu.napls.boostim.model.User;
import stu.napls.boostim.repository.UserRepository;
import stu.napls.boostim.service.UserService;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User findUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Override
    public User findUserBySessionId(String sessionId) {
        return userRepository.findBySessionId(sessionId);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
