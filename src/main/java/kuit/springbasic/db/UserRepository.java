package kuit.springbasic.db;

import kuit.springbasic.domain.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository {

    void insert(User user);

    User findByUserId(String userId);

    List<User> findAll();

    void changeUserInfo(User user);

    void update(User user);
}


