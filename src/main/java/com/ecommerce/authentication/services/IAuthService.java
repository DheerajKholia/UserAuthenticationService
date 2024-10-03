package com.ecommerce.authentication.services;

import com.ecommerce.authentication.models.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

public interface IAuthService {
    User signUp(String username, String password);
    Pair<User, MultiValueMap<String, String>> login(String username, String password);

    Boolean validateToken(String token, Long userid);
}
