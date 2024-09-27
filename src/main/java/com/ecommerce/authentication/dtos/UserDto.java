package com.ecommerce.authentication.dtos;

import com.ecommerce.authentication.models.BaseModel;
import com.ecommerce.authentication.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseModel {
    private String email;
    private Role role;
}
