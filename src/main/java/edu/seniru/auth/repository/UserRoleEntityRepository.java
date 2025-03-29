package edu.seniru.auth.repository;

import edu.seniru.auth.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity,Long> {
    Optional<UserRoleEntity> findByName(String name);
}
