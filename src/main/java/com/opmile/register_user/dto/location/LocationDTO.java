package com.opmile.register_user.dto.location;

import com.opmile.register_user.model.Location;

public record LocationDTO(
        Long id,
        String street,
        String city,
        String state,
        String country
) {
    public LocationDTO(Location location) {
        this(
                location.getId(),
                location.getStreet(),
                location.getCity(),
                location.getCity(),
                location.getCountry()
        );
    }
}
