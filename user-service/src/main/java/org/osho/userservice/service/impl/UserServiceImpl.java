package org.osho.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.osho.userservice.models.Role;
import org.osho.userservice.models.Token;
import org.osho.userservice.models.User;
import org.osho.userservice.repository.RoleRepository;
import org.osho.userservice.repository.TokenRepository;
import org.osho.userservice.repository.UserRepository;
import org.osho.userservice.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public User signUp(String email, String name, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        Role role = new Role();
        role.setValue("USER");
        roleRepository.save(role);
        user.setRoles(List.of(role));
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found for email "+ email);
        }
        if(!bCryptPasswordEncoder.matches(password, user.get().getHashedPassword())){
            throw new RuntimeException("Password not match");
        }
        Token token = generateToken(user.get());
        tokenRepository.save(token);
        return token;
    }

    @Override
    public User validateToken(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByTokenAndExpiryAtGreaterThan(token, new Date());
        if (tokenOptional.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }
        return tokenOptional.get().getUser();
    }

    private Token generateToken(User user){
        Token token = new Token();
        token.setUser(user);
        token.setToken(RandomStringUtils.randomAlphanumeric(64));
        LocalDate now = LocalDate.now();
        LocalDate expiry = now.plusDays(30);
        Date expirationDate = Date.from(expiry.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expirationDate);
        return token;
    }

}
