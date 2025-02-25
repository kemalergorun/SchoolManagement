package com.techproed.schoolmanagementbackendb326.payload.request.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLessonProgramForStudent {
    @NotNull(message = "select lesson program")
    @Size(min = 1,message = "lesson must full")
    private List<Long>lessonProgramId;
}
