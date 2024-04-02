package com.gizasystems.usermanagement;

import com.gizasystems.usermanagement.controller.UserController;
import com.gizasystems.usermanagement.model.dto.UserDTO;
import com.gizasystems.usermanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
class UserControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    public void createUser() {
        UserDTO userDTO = new UserDTO(1L, "Ahmed", "احمد", "ahmed@test.com", "password", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        when(userService.createUser(any())).thenReturn(Mono.just(userDTO));

        webTestClient.post()
                .uri("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDTO)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserDTO.class)
                .value(result -> {
                    assertEquals(userDTO.getUserId(), result.getUserId());
                    assertEquals(userDTO.getUsernameEn(), result.getUsernameEn());
                });
    }

}