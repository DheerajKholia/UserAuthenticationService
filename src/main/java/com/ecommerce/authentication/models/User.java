package com.ecommerce.authentication.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseModel{
    private String email;
    private String password;
    private Role role;
}
