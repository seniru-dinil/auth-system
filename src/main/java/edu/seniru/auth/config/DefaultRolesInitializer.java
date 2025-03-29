package edu.seniru.auth.config;

import edu.seniru.auth.entity.UserRoleEntity;
import edu.seniru.auth.repository.UserRoleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class DefaultRolesInitializer implements CommandLineRunner {

    private final UserRoleEntityRepository roleEntityRepository;

    @Override
    public void run(String... args) throws Exception {

        if(roleEntityRepository.findByName("ROLE_USER").isEmpty()){
            roleEntityRepository.save(UserRoleEntity.builder()
                            .name("ROLE_USER")
                    .build());
        }

        if(roleEntityRepository.findByName("ROLE_ADMIN").isEmpty()){
            roleEntityRepository.save(UserRoleEntity.builder()
                            .name("ROLE_ADMIN")
                    .build());
        }

    }
}
