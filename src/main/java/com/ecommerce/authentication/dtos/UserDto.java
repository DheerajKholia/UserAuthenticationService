package com.ecommerce.authentication.dtos;

import com.ecommerce.authentication.models.BaseModel;
import com.ecommerce.authentication.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto extends BaseModel {
    private String email;
    private Set<Role> roles = new HashSet<>();
}
