package edu.seniru.auth.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthResponse {
    private String email;
    private List<String> role;
    private String token;
}
