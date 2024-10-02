package com.ecommerce.authentication.controllers;

import com.ecommerce.authentication.dtos.*;
import com.ecommerce.authentication.exceptions.UserAlreadyExistException;
import com.ecommerce.authentication.models.User;
import com.ecommerce.authentication.services.IAuthService;
import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            User user = authService.signUp(signupRequestDto.getEmail(), signupRequestDto.getPassword());
            if (user == null) {
                throw new UserAlreadyExistException("Please try with different email");
            }
            return new ResponseEntity<>(from(user), HttpStatus.CREATED);
        }catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(LoginRequestDto loginRequestDto) {
        Pair<User, MultiValueMap<String,String>> userWithHeaders = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        User user = userWithHeaders.a;
        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }
        return new ResponseEntity<>(from(user),userWithHeaders.b,HttpStatus.OK);
    }

    public ResponseEntity<Boolean> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        return null;
    }
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){
        return null;
    }

    private UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
