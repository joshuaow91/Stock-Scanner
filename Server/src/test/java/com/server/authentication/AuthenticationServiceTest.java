package com.server.authentication;

import com.server.authentication.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

public class AuthenticationServiceTest {

    private CookieService cookieService;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        cookieService = new CookieService();
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void testCookieService() {
        String cookieName = "testCookie";
        String cookieValue = "testValue";

        cookieService.addHttpOnlyCookie(response, cookieName, cookieValue);
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());
        Cookie cookie = cookieCaptor.getValue();

        assertEquals(cookieName, cookie.getName());
        assertEquals(cookieValue, cookie.getValue());
        assertTrue(cookie.isHttpOnly());
        assertEquals("/", cookie.getPath());
    }
}
