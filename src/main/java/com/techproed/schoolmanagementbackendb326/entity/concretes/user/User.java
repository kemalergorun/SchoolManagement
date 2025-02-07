package com.techproed.schoolmanagementbackendb326.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techproed.schoolmanagementbackendb326.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="t_user")
public class User {

    private Long id;
    private String username;
    private String ssn;
    private String name;
    private String surname;
    private LocalDate birthday;
    private String birthplace;
    private String password;
    private String phoneNumber;
    private String email;
    private Boolean buildIn;
    private String motherName;
    private String fatherName;
    private String studentNumber;
    private boolean isActive;
    private Boolean isAdvisor;
    private Long advisorTeacherId;
    private Gender gender;
    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;






}
