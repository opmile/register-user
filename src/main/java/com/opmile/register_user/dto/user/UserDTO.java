package com.opmile.register_user.dto.user;

import com.opmile.register_user.dto.location.LocationDTO;
import com.opmile.register_user.model.Gender;
import com.opmile.register_user.model.User;

public record UserDTO(
        Long id,
        String fullName,
        String email,
        String username,
        int age,
        Gender gender,
        LocationDTO locationDTO
) {
    public UserDTO(User user) {
        this(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getAge(),
                user.getGender(),
                new LocationDTO(user.getLocation())
        );
    }
}
