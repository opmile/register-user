package com.opmile.register_user.dto.fetch_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FetchResponseDTO(
        List<UserApiDTO> results
) {}
