package edu.seniru.auth.dto;


import edu.seniru.auth.util.Status;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date createdDate;
    private Date updatedDate;
    private Boolean failedAttempt;
    private Status status;
}
