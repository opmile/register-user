package com.opmile.register_user.mapper;

import com.opmile.register_user.dto.fetch_api.LocationApiDTO;
import com.opmile.register_user.model.Location;

public class LocationMapper {
    public static Location toEntity(LocationApiDTO dto) {
        String street = dto.street().number() + " " + dto.street().name();
        return new Location(
                street,
                dto.city(),
                dto.state(),
                dto.country()
        );
    }
}
