package com.techproed.schoolmanagementbackendb326.entity.concretes.business;

import com.techproed.schoolmanagementbackendb326.entity.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonProgram {

    //cntrl shift v copy yapar
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Day day;

    private LocalTime startTime;

    private LocalTime stopTime;
    @ManyToMany
    @JoinTable(
            name = "lesson_program_lesson",
            joinColumns = @JoinColumn(name = "lessonProgram_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson> lessons;


    @ManyToOne
    private EducationTerm educationTerm;
}
