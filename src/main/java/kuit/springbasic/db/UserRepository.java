package kuit.springbasic.db;

import kuit.springbasic.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface UserRepository {

    void insert(User user);

    User findByUserId(String userId);

    Collection<User> findAll();

    void changeUserInfo(User user);

    void update(User user);
}


