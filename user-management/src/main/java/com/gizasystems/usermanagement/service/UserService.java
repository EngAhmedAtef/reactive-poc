package com.gizasystems.usermanagement.service;

import com.gizasystems.cssdb.dto.UserDTO;
import com.gizasystems.cssdb.entity.User;
import com.gizasystems.cssdb.util.Mapper;
import com.gizasystems.usermanagement.exception.CannotUpdateIdException;
import com.gizasystems.usermanagement.exception.UserFieldException;
import com.gizasystems.usermanagement.exception.UserNotFoundException;
import com.gizasystems.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Mono<UserDTO> createUser(UserDTO userDTO) {
        User user = Mapper.map(userDTO);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        user.setCreatedOn(currentTimestamp);
        user.setModifiedOn(currentTimestamp);

        String encodedPassword = hashPassword(user.getPassword());
        user.setPassword(encodedPassword);

        return repository.createUser(user).map(Mapper::map);
    }

    public Flux<UserDTO> findAll() {
        return repository.findAll().map(Mapper::map);
    }

    public Mono<UserDTO> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .map(Mapper::map);
    }

    public Mono<UserDTO> update(Long id, String fieldName, Object newValue) {
        return repository.findById(id).switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                .flatMap(user -> {
                    User updatedUser = updateUser(user, fieldName, newValue);
                    return repository.update(updatedUser).map(Mapper::map);
                });
    }

    private User updateUser(User user, String fieldName, Object newValue) {
        if (fieldName.equals("userId"))
            throw new CannotUpdateIdException();

        try {
            Field field = user.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (fieldName.equals("password"))
                newValue = hashPassword(String.valueOf(newValue));
            field.set(user, newValue);
            user.setModifiedOn(new Timestamp(System.currentTimeMillis()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new UserFieldException(fieldName);
        }

        return user;
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
