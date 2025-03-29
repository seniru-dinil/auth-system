package edu.seniru.auth.entity;


import edu.seniru.auth.util.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date created;
    private Date updated;
    private Boolean failedAttempt;
    private Status status;
    private String resetToken;
    private LocalDateTime lastLogin;



    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRoleEntity> userRoleEntities=new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
          return userRoleEntities.stream().map(role->new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    protected void onCreate() {
        created = new Date();
        updated = created;
        status = Status.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
}
