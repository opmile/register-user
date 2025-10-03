package com.opmile.register_user.dto.user;

import com.opmile.register_user.dto.location.LocationCreateDTO;

public record UserCreateDTO(
        String fullName,
        String email,
        String username,
        int age,
        String gender,
        LocationCreateDTO location
) {
}
