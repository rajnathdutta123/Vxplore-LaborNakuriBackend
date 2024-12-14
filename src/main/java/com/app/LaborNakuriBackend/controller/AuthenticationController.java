package com.app.LaborNakuriBackend.controller;

import com.app.LaborNakuriBackend.dto.LoginResponse;
import com.app.LaborNakuriBackend.dto.LoginUserDto;
import com.app.LaborNakuriBackend.dto.RegisterUserDto;
import com.app.LaborNakuriBackend.entity.User;
import com.app.LaborNakuriBackend.service.AuthenticationService;
import com.app.LaborNakuriBackend.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome this endpoint is not secure");
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        // Create an HttpOnly cookie
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .secure(true) // Use 'true' in HTTPS environments
                .path("/")    // Available for the whole domain
                .maxAge(Duration.ofHours(2))
                .build();
        //LoginResponse loginResponse = new LoginResponse(jwtToken,jwtService.getExpirationTime());
        //return ResponseEntity.ok(jwtToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Login Successful");
    }

    @GetMapping("/secure")
    public ResponseEntity<String> secureEndpoint(@CookieValue(name = "jwt", required = false) String token,@RequestParam String email) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        if (token == null || !jwtService.isTokenValid(token,userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }
        return ResponseEntity.ok("This is a secure endpoint");
    }
}
