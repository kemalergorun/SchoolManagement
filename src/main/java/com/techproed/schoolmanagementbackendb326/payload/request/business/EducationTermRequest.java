package com.techproed.schoolmanagementbackendb326.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techproed.schoolmanagementbackendb326.entity.enums.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationTermRequest {
    @NotNull(message = "term must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Term term;

    @NotNull(message = "start date must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "end date must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @NotNull(message = "registration must not be empty")
    private LocalDate lastRegistrationDate;
}
