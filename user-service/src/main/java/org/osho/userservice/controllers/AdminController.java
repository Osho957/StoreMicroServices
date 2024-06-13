package org.osho.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.osho.userservice.dtos.SignUpRequestDto;
import org.osho.userservice.dtos.UserDto;
import org.osho.userservice.models.User;
import org.osho.userservice.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto userDto){
        User user = adminService.signUp(userDto.getEmail(), userDto.getName(), userDto.getPassword());
        return UserDto.from(user);
    }
}
