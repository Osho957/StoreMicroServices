package com.osho.product.service;

import com.osho.product.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RestTemplate restTemplate;

    public boolean validateToken(String token) {
        UserDto userDto = restTemplate.postForObject("http://localhost:8081/users/validate/" +  token, null, UserDto.class);
        return userDto != null;
    }
}
