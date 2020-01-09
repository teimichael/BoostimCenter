package stu.napls.boostimcenter.service.impl;

import org.springframework.stereotype.Service;
import stu.napls.boostimcenter.model.User;
import stu.napls.boostimcenter.repository.UserRepository;
import stu.napls.boostimcenter.service.UserService;

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
