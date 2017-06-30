package com.test;

import com.test.userservice.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryMock implements IUserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public int create(User user) {

        int id = users.size() + 1;
        user.setId(id);
        users.add(user);
        return id;
    }

    @Override
    public Optional<User> get(int id) {

        return users.stream().filter(u -> u.getId() == id).findAny();
    }
}