package com.gizasystems.activitylogservice.controller;

import com.gizasystems.activitylogservice.dto.ActivityTypeDTO;
import com.gizasystems.activitylogservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService service;

    @PostMapping("create")
    public Mono<ActivityTypeDTO> createActivityType(@RequestBody ActivityTypeDTO activityTypeDTO) {
        return service.createActivityType(activityTypeDTO);
    }

    @GetMapping
    public Flux<ActivityTypeDTO> findAllActivityTypes() {
        return service.findAllActivityTypes();
    }

    @GetMapping("{id}")
    public Flux<ActivityTypeDTO> findUserActivities(@PathVariable("id") Long userId) {
        return service.findUserActivities(userId);
    }

}
