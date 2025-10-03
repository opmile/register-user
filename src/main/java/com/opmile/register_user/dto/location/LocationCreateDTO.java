package com.opmile.register_user.dto.location;

import com.opmile.register_user.model.Location;

public record LocationCreateDTO(
        String street,
        String city,
        String state,
        String country
) implements ILocationData {
    public LocationCreateDTO(Location location) {
        this(
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getCountry()
        );
    }
}
