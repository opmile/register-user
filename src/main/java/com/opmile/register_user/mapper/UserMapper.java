package com.opmile.register_user.mapper;

import com.opmile.register_user.dto.fetch_api.UserApiDTO;
import com.opmile.register_user.model.Gender;
import com.opmile.register_user.model.Location;
import com.opmile.register_user.model.User;

public class UserMapper {
    public static User toEntity(UserApiDTO dto) {
        Gender gender = Gender.fromString(dto.gender());
        Location location = LocationMapper.toEntity(dto.location());
        String fullName = dto.name().first() + " " + dto.name().last();

        return new User(
                fullName,
                dto.email(),
                dto.login().username(),
                dto.dob().age(),
                gender,
                location
        );
    }
}
