package com.hacknc.uncc.service;

import com.hacknc.uncc.entity.Role;
import com.hacknc.uncc.entity.User;
import com.hacknc.uncc.exception.UserException;
import com.hacknc.uncc.repository.UserRepository;
import com.hacknc.uncc.request.AuthenticationRequest;
import com.hacknc.uncc.request.RegisterRequest;
import com.hacknc.uncc.response.AuthenticationResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        validateRegisterUser(request);
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (!existingUser.isEmpty()) {
            throw new UserException("Duplicate Entry", List.of(request.getEmail() + " email id already registered"));
        }
        userRepository.save(user);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("role", user.getRole());
        String jwtToken = jwtService.generateToken(hashMap, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void validateRegisterUser(RegisterRequest user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(user);
        if(!violations.isEmpty()){
            List<String> errors = new ArrayList<>();
            for(ConstraintViolation<RegisterRequest> violation: violations){
                errors.add(violation.getMessage());
            }
            throw new UserException("Validation Error", errors);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        validateAuthenticateUser(request);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch(Exception e){
            throw new UserException("Invalid Email or Password", List.of("Invalid Email or Password"));
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("User not found", List.of("User not found for email: " + request.getEmail())));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("role", user.getRole());
        String jwtToken = jwtService.generateToken(hashMap, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void validateAuthenticateUser(AuthenticationRequest user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AuthenticationRequest>> violations = validator.validate(user);
        if(!violations.isEmpty()){
            List<String> errors = new ArrayList<>();
            for(ConstraintViolation<AuthenticationRequest> violation: violations){
                errors.add(violation.getMessage());
            }
            throw new UserException("Validation Error", errors);
        }
    }
}
