package edu.seniru.auth.controller;


import edu.seniru.auth.dto.AuthRequest;
import edu.seniru.auth.dto.AuthResponse;
import edu.seniru.auth.dto.RegisterRequest;
import edu.seniru.auth.entity.UserEntity;
import edu.seniru.auth.repository.UserEntityRepository;
import edu.seniru.auth.service.UserRegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserEntityRepository userEntityRepository;

    private final AuthenticationManager authenticationManager;

    private final UserRegistrationServiceImpl userRegistrationService;
    @GetMapping
    public String hello(){
        return "hello user";
    }


    @PostMapping("/register")
    public UserEntity saveUser(@RequestBody RegisterRequest request){
        return userRegistrationService.createUser(request);
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserEntity userEntity = userEntityRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("not a valid user"));
        return AuthResponse.builder()
                .email(userEntity.getEmail())
                .role(userEntity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
