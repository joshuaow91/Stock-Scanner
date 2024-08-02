package com.server.authentication.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequestDTO {
    private final String username;
    private final String email;
    private final String password;

    @JsonCreator
    public RegisterRequestDTO(@JsonProperty("username") String username,
                              @JsonProperty("email") String email,
                              @JsonProperty("password") String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private RegisterRequestDTO(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String username;
        private String email;
        private String password;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterRequestDTO build() {
            return new RegisterRequestDTO(this);
        }
    }
}
