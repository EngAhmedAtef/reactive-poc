package com.gizasystems.usermanagement.util;

import com.gizasystems.usermanagement.dto.UserDTO;
import com.gizasystems.usermanagement.entity.User;

public final class Mapper {
    public static UserDTO map(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsernameEn(),
                user.getUsernameAr(),
                user.getUserEmail(),
                user.getPassword(),
                user.getCreatedOn(),
                user.getModifiedOn()
        );
    }

    public static User map(UserDTO dto) {
        return new User(
                dto.getUserId(),
                dto.getUsernameEn(),
                dto.getUsernameAr(),
                dto.getUserEmail(),
                dto.getPassword(),
                dto.getCreatedOn(),
                dto.getModifiedOn()
        );
    }
}
