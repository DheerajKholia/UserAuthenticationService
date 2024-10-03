package com.ecommerce.authentication.dtos;

import com.ecommerce.authentication.models.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequest {
    private String token;
    private Long userid;
}
