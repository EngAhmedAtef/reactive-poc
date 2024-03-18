package com.gizasystems.usermanagement.controller;

import com.gizasystems.usermanagement.dto.UserDTO;
import com.gizasystems.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("create")
    public Mono<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return service.createUser(userDTO);
    }

    @GetMapping
    public Flux<UserDTO> getUsers() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Mono<UserDTO> getUserById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PutMapping("{id}/{field}/{value}")
    public Mono<UserDTO> updateUser(@PathVariable("id") Long id, @PathVariable("field") String fieldName, @PathVariable("value") Object newValue) {
        return service.update(id, fieldName, newValue);
    }

    @PutMapping("{id}/reset/{value}")
    public Mono<UserDTO> resetPassword(@PathVariable("id") Long id, @PathVariable("value") String newPassword) {
        return service.update(id, "password", newPassword);
    }

    // TODO: Pay the bill

    // TODO: GetUserNotification

}
