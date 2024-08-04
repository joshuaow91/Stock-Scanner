package com.server.authentication.dto;

import com.server.users.dto.UserDTO;

public class AuthenticationResponseDTO {
    private final String accessToken;
    private final String refreshToken;
    private final UserDTO user;

    public AuthenticationResponseDTO(String accessToken, String refreshToken, UserDTO user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserDTO getUser() {
        return user;
    }
}
