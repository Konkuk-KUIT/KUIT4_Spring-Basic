package kuit.springbasic.db;

import kuit.springbasic.domain.User;

import java.util.Collection;

public class JdbcUserRepository implements UserRepository{
    @Override
    public void insert(User user) {

    }

    @Override
    public User findByUserId(String userId) {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public void changeUserInfo(User user) {

    }

    @Override
    public void update(User user) {

    }
}
