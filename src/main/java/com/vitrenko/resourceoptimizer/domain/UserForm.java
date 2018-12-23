package com.vitrenko.resourceoptimizer.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
public class UserForm {

    @NotEmpty
    private String email;
    private String firstName;
    private String lastName;
    private String newPassword;
}
