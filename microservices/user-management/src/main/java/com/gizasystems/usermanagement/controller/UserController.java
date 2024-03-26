package com.gizasystems.usermanagement.controller;

import com.gizasystems.usermanagement.model.PasswordResetRequest;
import com.gizasystems.usermanagement.model.UpdateRequest;
import com.gizasystems.usermanagement.model.dto.UserDTO;
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

    @PutMapping("{id}")
    public Mono<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UpdateRequest updateRequest) {
        return service.update(id, updateRequest.getField(), updateRequest.getValue());
    }

    @PutMapping("{id}/reset")
    public Mono<UserDTO> resetPassword(@PathVariable("id") Long id, @RequestBody PasswordResetRequest resetRequest) {
        return service.update(id, "password", resetRequest.getValue());
    }

    @PutMapping("{userId}/bills/pay")
    public Mono<Object> payBill(@PathVariable("userId") Long userId, @RequestParam("id") Long billId) {
        return service.payBill(userId, billId);
    }

    // TODO: GetUserNotification

}
