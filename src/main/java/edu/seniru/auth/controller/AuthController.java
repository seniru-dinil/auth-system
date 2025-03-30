package edu.seniru.auth.controller;


import edu.seniru.auth.dto.RegisterRequest;
import edu.seniru.auth.entity.UserEntity;
import edu.seniru.auth.service.UserRegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRegistrationServiceImpl userRegistrationService;
    @GetMapping
    public String hello(){
        return "hello user";
    }


    @PostMapping("/register")
    public UserEntity saveUser(@RequestBody RegisterRequest request){
        return userRegistrationService.createUser(request);
    }
}
