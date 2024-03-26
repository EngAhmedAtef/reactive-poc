package com.gizasystems.activitylogservice.service;

import com.gizasystems.activitylogservice.dto.ActivityLogDTO;
import com.gizasystems.activitylogservice.dto.ActivityTypeDTO;
import com.gizasystems.activitylogservice.entity.ActivityLog;
import com.gizasystems.activitylogservice.entity.ActivityType;
import com.gizasystems.activitylogservice.repository.ActivityRepository;
import com.gizasystems.activitylogservice.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository repository;

    public Mono<ActivityTypeDTO> createActivityType(ActivityTypeDTO dto) {
        ActivityType activityType = Mapper.map(dto);
        return repository.createActivityType(activityType).map(Mapper::map);
    }

    public Flux<ActivityTypeDTO> findAllActivityTypes() {
        return repository.findAllActivityTypes().map(Mapper::map);
    }

    public Flux<ActivityTypeDTO> findUserActivities(Long userId) {
        return repository.findUserActivities(userId).map(Mapper::map);
    }

    // TODO Add a listener that executes this method
    public Mono<ActivityLogDTO> logActivity(Long userId, Long activityId) {
        ActivityLog activityLog = new ActivityLog(null, userId, activityId, null);
        return repository.logActivity(activityLog).map(Mapper::map);
    }

}
