package com.ecommerce.authentication.controllers;

import com.ecommerce.authentication.dtos.LoginRequestDto;
import com.ecommerce.authentication.dtos.SignupRequestDto;
import com.ecommerce.authentication.dtos.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //Signup
    //Login
    //ForgotPassword
    //Logout

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return null;
    }

    @PostMapping("/login")
    public UserDto login(LoginRequestDto loginRequestDto) {
        return null;
    }
}
