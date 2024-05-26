package org.osho.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.osho.userservice.dtos.LoginRequestDto;
import org.osho.userservice.dtos.SignUpRequestDto;
import org.osho.userservice.dtos.UserDto;
import org.osho.userservice.models.Token;
import org.osho.userservice.models.User;
import org.osho.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto userDto){
       User user = userService.signUp(userDto.getEmail(), userDto.getName(), userDto.getPassword());
       return UserDto.from(user);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginDto){
        return userService.login(loginDto.getEmail(), loginDto.getPassword());
    }

    @PostMapping("/validate/{token}")
    public UserDto validate(@PathVariable(value = "token") String token){
        try {
            User user = userService.validateToken(token);
            return UserDto.from(user);
        }catch (Exception e){
            return null;
        }

    }



}
