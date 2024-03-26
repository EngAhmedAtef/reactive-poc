package com.gizasystems.activitylogservice.util;

import com.gizasystems.activitylogservice.dto.ActivityLogDTO;
import com.gizasystems.activitylogservice.dto.ActivityTypeDTO;
import com.gizasystems.activitylogservice.entity.ActivityLog;
import com.gizasystems.activitylogservice.entity.ActivityType;

public final class Mapper {
    public static ActivityLog map(ActivityLogDTO dto) {
        return new ActivityLog(dto.getId(), dto.getUserId(), dto.getActivityId(), dto.getCreatedOn());
    }

    public static ActivityLogDTO map(ActivityLog activityLog) {
        return new ActivityLogDTO(
                activityLog.getId(),
                activityLog.getUserId(),
                activityLog.getActivityId(),
                activityLog.getCreatedOn());
    }

    public static ActivityType map(ActivityTypeDTO dto) {
        return new ActivityType(dto.getId(), dto.getActivityNameEn(), dto.getActivityNameAr());
    }

    public static ActivityTypeDTO map(ActivityType activityType) {
        return new ActivityTypeDTO(activityType.getId(), activityType.getActivityNameEn(), activityType.getActivityNameAr());
    }
}
