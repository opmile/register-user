package com.opmile.register_user.dto.fetch_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationApiDTO(
        LocationStreetApiDTO street,
        String city,
        String state,
        String country
) {
}
