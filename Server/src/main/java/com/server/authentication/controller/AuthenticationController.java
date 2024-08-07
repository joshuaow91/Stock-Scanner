package com.server.authentication.controller;

import com.server.authentication.dto.AuthenticationRequestDTO;
import com.server.authentication.dto.AuthenticationResponseDTO;
import com.server.authentication.dto.RegisterRequestDTO;
import com.server.authentication.service.LoginService;
import com.server.authentication.service.LogoutService;
import com.server.authentication.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class AuthenticationController {

    private final LoginService service;
    private final RegistrationService registrationService;
    private final LogoutService logoutService;

    @Autowired
    public AuthenticationController(LoginService service, RegistrationService registrationService, LogoutService logoutService) {
        this.service = service;
        this.registrationService = registrationService;
        this.logoutService = logoutService;
    }

    @PostMapping
    public AuthenticationResponseDTO register(@RequestBody RegisterRequestDTO request, HttpServletResponse response) {
        return registrationService.register(request, response);
    }

    @PostMapping("/session")
    public AuthenticationResponseDTO authenticate(@RequestBody AuthenticationRequestDTO request, HttpServletResponse response) {
        return service.authenticate(request, response);
    }

    @DeleteMapping("/session")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logoutService.logout(request, response, null);
        return "Logged out successfully";
    }

    @PostMapping("/session/token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
