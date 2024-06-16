package org.osho.userservice.service;


import org.osho.userservice.models.Token;
import org.osho.userservice.models.User;
import org.springframework.stereotype.Service;


public interface UserService {

    User signUp(String email, String name, String password);
    Token login(String email, String password);
    User validateToken(String token);
    User getUserById(Long id);
}
