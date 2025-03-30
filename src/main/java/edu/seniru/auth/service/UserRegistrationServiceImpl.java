package edu.seniru.auth.service;


import edu.seniru.auth.dto.RegisterRequest;
import edu.seniru.auth.entity.UserEntity;
import edu.seniru.auth.entity.UserRoleEntity;
import edu.seniru.auth.repository.UserEntityRepository;
import edu.seniru.auth.repository.UserRoleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl {

    @Value("${application.support.admin.list}")
    private List<String> list;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userEntityRepository;
    private final UserRoleEntityRepository roleEntityRepository;

    public UserEntity createUser(RegisterRequest request) {
        Optional<UserEntity> byEmail = userEntityRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            return null;
        }
        return userEntityRepository.save(UserEntity.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRoleEntities(getRoles(request.getEmail()))
                .build());
    }


    public Set<UserRoleEntity> getRoles(String email) {
        UserRoleEntity roleAdmin = roleEntityRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("role not found"));
        UserRoleEntity roleUser = roleEntityRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("role not found"));
        if (list.contains(email)){
         return   Set.of(roleAdmin,roleUser);
        }
        return Set.of(roleUser);
    }
}
