package org.osho.userservice.service;

import org.osho.userservice.models.User;

public interface AdminService {
    User signUp(String email, String name, String password);
}

