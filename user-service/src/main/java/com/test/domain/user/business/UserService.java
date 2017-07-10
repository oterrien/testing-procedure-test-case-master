package com.test.domain.user.business;

import com.test.domain.user.api.IPassword;
import com.test.domain.user.api.IUser;
import com.test.domain.user.api.IUserService;
import com.test.domain.user.spi.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class UserService<TU extends IUser> implements IUserService<TU> {

    private final IUserRepository<TU> userRepository;

    @Override
    public Optional<TU> get(int id) {
        return userRepository.find(id);
    }

    @Override
    public List<TU> getAll() {
        return userRepository.findAll();
    }

    @Override
    public int create(TU user) {
        return userRepository.create(user);
    }

    @Override
    public void update(int id, TU user) {
        user.setId(id);
        userRepository.update(id, user);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void resetPassword(int id, IPassword newPassword) {

        get(id).ifPresent(u -> {
            u.setPassword(newPassword);
            update(id, u);
        });
    }

    @Override
    public boolean isPasswordCorrect(int id, IPassword password) {

        return get(id).
                map(u -> u.getPassword().compareTo(password) == 0).
                orElse(false);
    }
}
