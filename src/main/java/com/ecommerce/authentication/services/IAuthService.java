package com.ecommerce.authentication.services;

import com.ecommerce.authentication.models.User;

public interface IAuthService {
    User signUp(String username, String password);
    User login(String username, String password);
}
