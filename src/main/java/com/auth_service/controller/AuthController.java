package com.auth_service.controller;

import com.auth_service.dto.LoginDto;
import com.auth_service.dto.SignInDto;
import com.auth_service.service.AuthService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody SignInDto dto){
        return ResponseEntity.ok(authService.signIn(dto));
    }

    @PostMapping("/login")
    Map<String, Object> loginHandler(@Valid @RequestBody LoginDto dto) {
        return authService.login(dto);
    }
}
