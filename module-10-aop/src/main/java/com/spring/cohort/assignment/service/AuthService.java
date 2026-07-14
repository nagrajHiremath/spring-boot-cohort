package com.spring.cohort.assignment.service;

import com.spring.cohort.assignment.dto.LoginRequest;
import com.spring.cohort.assignment.dto.LoginResponse;
import com.spring.cohort.assignment.dto.SignUpRequest;
import com.spring.cohort.assignment.dto.SignUpResponse;
import com.spring.cohort.assignment.entity.User;
import com.spring.cohort.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

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

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));

        return LoginResponse.builder()
                .token(jwtService.generateAccessToken((User) authentication.getPrincipal()))
                .build();
    }
}
