package org.osho.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.osho.userservice.models.Role;
import org.osho.userservice.models.User;
import org.osho.userservice.repository.RoleRepository;
import org.osho.userservice.repository.TokenRepository;
import org.osho.userservice.repository.UserRepository;
import org.osho.userservice.service.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {


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
        role.setValue("ADMIN");
        roleRepository.save(role);
        user.setRoles(List.of(role));
        return userRepository.save(user);
    }
}
