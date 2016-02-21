package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.User;

public interface UserService {

    String getCurrentUid();

    User findByCurrentUser();

    Iterable<User>findAll();

    Iterable<User>findAllPublic();

    User findByUid(String username);

    User saveByCurrentUser(User user);

    User save(User user);
}
