package com.server.dto;

import com.server.users.Users;

public class AuthenticationResponseDTO {
    private final String accessToken;
    private final String refreshToken;
    private final Users user;

    private AuthenticationResponseDTO(Builder builder) {
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
        this.user = builder.user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Users getUser() {
        return user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String accessToken;
        private String refreshToken;
        private Users user;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder user(Users user) {
            this.user = user;
            return this;
        }

        public AuthenticationResponseDTO build() {
            return new AuthenticationResponseDTO(this);
        }
    }
}
