// AuthDto.java
package com.bondsbackend.ms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthDto {
    private String username;
    private String password;
}