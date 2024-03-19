package com.gizasystems.usermanagement.service;

import com.gizasystems.csslib.enums.PaymentStatus;
import com.gizasystems.csslib.dto.PaymentRequestDTO;

import com.gizasystems.usermanagement.dto.UserDTO;
import com.gizasystems.usermanagement.entity.User;
import com.gizasystems.csslib.enums.ArtemisTopics;
import com.gizasystems.usermanagement.exception.CannotUpdateIdException;
import com.gizasystems.usermanagement.exception.UserFieldException;
import com.gizasystems.usermanagement.exception.UserNotFoundException;
import com.gizasystems.usermanagement.repository.UserRepository;
import com.gizasystems.usermanagement.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final JmsTemplate jmsTemplate;

    public Mono<UserDTO> createUser(UserDTO userDTO) {
        User user = Mapper.map(userDTO);

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
            if (fieldName.equals("password"))
                newValue = hashPassword(String.valueOf(newValue));

            Field field = user.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(user, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new UserFieldException(fieldName);
        }

        return user;
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public Mono<String> payBill(Long userId, Long billId) {
        jmsTemplate.convertAndSend(ArtemisTopics.USER_BILL.topic(), new PaymentRequestDTO(billId, userId, PaymentStatus.INITIATED));
        return Mono.just("Request has been sent. You'll get notified after it's complete.");
    }
}
