package com.test.infra.user;

import com.test.domain.user.spi.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryServiceAdapter implements IUserRepository<UserEntity> {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserEntity> read(int id) {
        return Optional.ofNullable(userJpaRepository.findOne(id));
    }

    @Override
    public int create(UserEntity userEntity) {

        userEntity.setId(0);
        userEntity = userJpaRepository.save(userEntity);
        return userEntity.getId();
    }

    @Override
    public void update(int id, UserEntity userEntity) {
        userEntity.setId(id);
        userJpaRepository.save(userEntity);
    }

    @Override
    public void delete(int id) {
        read(id).ifPresent(p -> userJpaRepository.delete(p.getId()));
    }
}