package com.ecommerce.authentication.dtos;

import com.ecommerce.authentication.models.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDto extends BaseModel {
    private String email;
    private String password;
}
