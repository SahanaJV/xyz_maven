package com.accenture.xyz_maven.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Response 
{
    String message;
    Boolean status;
    Boolean isException = false;
}
