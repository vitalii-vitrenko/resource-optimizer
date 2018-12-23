package com.vitrenko.resourceoptimizer.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Accessors(chain = true)
@Document("user")
public class AppUser {

    @Id
    private ObjectId id;
    @Email
    @NotBlank
    @Indexed(unique = true)
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @CreatedDate
    private LocalDateTime createDate;
    @NotEmpty
    private Set<UserRole> userRoles = EnumSet.of(UserRole.USER);
    private List<Task> tasks = new ArrayList<>();
}
