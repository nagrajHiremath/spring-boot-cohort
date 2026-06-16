package com.spring.cohort.assignment.service;

import com.spring.cohort.assignment.dto.LoginRequest;
import com.spring.cohort.assignment.dto.LoginResponse;
import com.spring.cohort.assignment.dto.SignUpRequest;
import com.spring.cohort.assignment.dto.SignUpResponse;
import com.spring.cohort.assignment.entity.User;
import com.spring.cohort.assignment.entity.enums.SubscriptionPlan;
import com.spring.cohort.assignment.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthService {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;
    SessionService sessionService;


    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Optional<User> user = userRepository.findByUserName(signUpRequest.getUserName());

        if(user.isPresent()){
            throw new BadCredentialsException("Already signed up");
        }

        User newUser = modelMapper.map(signUpRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(newUser);

        return modelMapper.map(newUser, SignUpResponse.class);
    }

    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse httpServletResponse) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));

        User user = (User) authentication.getPrincipal();
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);

        httpServletResponse.addCookie(cookie);

        sessionService.createSession(refreshToken, user);

        return LoginResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .build();
    }

    public LoginResponse refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String oldRefreshToken = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow();

        sessionService.validateSession(oldRefreshToken);
        UUID id = jwtService.getUserIdFromToken(oldRefreshToken);
        User user = userRepository.findById(id).orElseThrow();

        String newRefreshToken = jwtService.generateRefreshToken(user);
        sessionService.rotateRefreshToken(oldRefreshToken, newRefreshToken);

        Cookie cookie = new Cookie("refreshToken", newRefreshToken);

        httpServletResponse.addCookie(cookie);

        return LoginResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(newRefreshToken)
                .build();
    }

    public void logOut(HttpServletRequest httpServletRequest) {
        String refreshToken = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow();

        sessionService.deleteSession(refreshToken);
    }

    public boolean hasPlan(String requiredPlan){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        User user = (User) authentication.getPrincipal();

        assert user != null;
        SubscriptionPlan userPlan = user.getSubscriptionPlan();
        SubscriptionPlan required = SubscriptionPlan.valueOf(requiredPlan);

        return userPlan.ordinal() >= required.ordinal();
    }
}
