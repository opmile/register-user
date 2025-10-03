package com.opmile.register_user.dto.user;

import com.opmile.register_user.dto.location.LocationCreateDTO;
import com.opmile.register_user.dto.location.LocationUpdateDTO;

public record UserUpdateDTO(
        String fullName,
        String email,
        String username,
        int age,
        String gender,
        LocationUpdateDTO location
) {
}
