package com.opmile.register_user.dto.fetch_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserApiDTO(
        String gender,
        NameApiDTO name,
        LocationApiDTO location,
        String email,
        LoginApiDTO login,
        DobApiDTO dob
) {
}
