package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.dto.LoginRequest;
import com.spring.cohort.assignment.dto.LoginResponse;
import com.spring.cohort.assignment.dto.SignUpRequest;
import com.spring.cohort.assignment.dto.SignUpResponse;
import com.spring.cohort.assignment.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {

    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        return ResponseEntity.ok(authService.login(loginRequest, httpServletResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return ResponseEntity.ok(authService.refresh(httpServletRequest, httpServletResponse));
    }

    @PostMapping("/logOut")
    public ResponseEntity<Void> logOut(HttpServletRequest httpServletRequest){
        authService.logOut(httpServletRequest);
        return ResponseEntity.noContent().build();
    }

}
