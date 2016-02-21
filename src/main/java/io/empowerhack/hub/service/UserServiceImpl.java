package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.User;
import io.empowerhack.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public String getCurrentUid() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User findByCurrentUser() {
        return findByUid(getCurrentUid());
    }

    public Iterable<User>findAll() {
        if (findByCurrentUser() == null) {
            return findAllPublic();
        }

        return userRepository.findAll();
    }

    public Iterable<User>findAllPublic() {
        return userRepository.findAllPublic();
    }

    public User findByUid(String username) {
        return userRepository.findByUid(username);
    }

    public User saveByCurrentUser(User user) {
        user.setUid(getCurrentUid());

        return save(user);
    }

    public User save(User user) {
        User existingUser = findByUid(user.getUid());
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPrivate(user.isPrivate());
            existingUser.setAvailability(user.getAvailability());

            return userRepository.save(existingUser);
        }

        return userRepository.save(user);
    }
}
