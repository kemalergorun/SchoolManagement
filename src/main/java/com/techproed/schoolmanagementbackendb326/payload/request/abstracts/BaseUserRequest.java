package com.techproed.schoolmanagementbackendb326.payload.request.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseUserRequest extends AbstractUserRequest{

    @NotNull(message = "please enter password")
    @Size(min = 8,max = 20,message = "your password must be 8-20 characters")
    private String password;

    private Boolean buildIn=false;
}
