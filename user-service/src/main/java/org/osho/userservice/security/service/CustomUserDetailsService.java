package org.osho.userservice.security.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.osho.userservice.models.User;
import org.osho.userservice.repository.UserRepository;
import org.osho.userservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@JsonDeserialize
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return new CustomUserDetails(optionalUser.get());
    }
}
