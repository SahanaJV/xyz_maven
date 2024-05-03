package com.accenture.xyz_maven.buisnessBeans;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserBean 
{
    private String username;
    private String password;
}
