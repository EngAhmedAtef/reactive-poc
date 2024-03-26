package com.gizasystems.usermanagement.service;

import com.gizasystems.common.dto.CreateUserMessage;
import com.gizasystems.common.dto.PaymentRequestMessage;
import com.gizasystems.common.enums.Event;
import com.gizasystems.common.enums.PaymentStatus;
import com.gizasystems.usermanagement.entity.User;
import com.gizasystems.usermanagement.exception.CannotUpdateIdException;
import com.gizasystems.usermanagement.exception.UserFieldException;
import com.gizasystems.usermanagement.exception.UserNotFoundException;
import com.gizasystems.usermanagement.infrastructure.MessageSender;
import com.gizasystems.usermanagement.model.dto.UserDTO;
import com.gizasystems.usermanagement.repository.UserRepository;
import com.gizasystems.usermanagement.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final MessageSender messageSender;
    private final ReactiveRedisTemplate<String, Object> redisTemplate;
    private final WebClient webClient;

    private static final String BILL_SERVICE_URL = "http://localhost:8083";

    public Mono<UserDTO> createUser(UserDTO userDTO) {
        User user = Mapper.map(userDTO);

        String encodedPassword = hashPassword(user.getPassword());
        user.setPassword(encodedPassword);
        log.info("Called createUser(). Username English: {}, Username Arabic: {}", userDTO.getUsernameEn(), userDTO.getUsernameAr());
        return repository.createUser(user)
                .doOnSuccess(dbUser ->
                        messageSender.sendMessage(
                                "atef.css.user",
                                new CreateUserMessage(dbUser.getUserId(), dbUser.getUserEmail()),
                                "event",
                                Event.CREATE_USER.title()))
                .map(Mapper::map);
    }

    public Flux<UserDTO> findAll() {
        log.info("Called findAll()");
        return repository.findAll().map(Mapper::map);
    }

    public Mono<UserDTO> findById(Long id) {
        log.info("Called findById. ID: {}", id);
        return redisTemplate.opsForValue().get(String.format("student:%d", id))
                .switchIfEmpty(
                        repository.findById(id)
                                .switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                                .flatMap(user -> redisTemplate.opsForValue().set(String.format("student:%d", user.getUserId()), Mapper.map(user))
                                        .thenReturn(user))
                                .map(Mapper::map))
                .map(obj -> (UserDTO) obj);
    }

    public Mono<UserDTO> update(Long id, String fieldName, Object newValue) {
        log.info("Updating user id {}. Target field: {}.", id, fieldName);
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

    public Mono<Object> payBill(Long userId, Long billId) {
        log.info("User id {} is playing bill id {}", userId, billId);
        messageSender.sendMessage("atef.css.user", new PaymentRequestMessage(billId, userId, PaymentStatus.INITIATED), "event", Event.PAY_BILL_REQUEST.title());
        return webClient.get()
                .uri(BILL_SERVICE_URL + String.format("/users/%d/pay/%d", userId, billId))
                .retrieve()
                .bodyToMono(Object.class);
    }
}
