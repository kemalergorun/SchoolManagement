package com.techproed.schoolmanagementbackendb326.payload.mappers;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonProgramRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonProgramResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class LessonProgramMapper {

    public LessonProgram mapLessonProgramRequestToLessonProgram(
            LessonProgramRequest lessonProgramRequest,
            List<Lesson> lessonSet, EducationTerm educationTerm){
        return LessonProgram.builder()
                .startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .day(lessonProgramRequest.getDay())
                .lessons(lessonSet)
                .educationTerm(educationTerm)
                .build();

    }
    public LessonProgramResponse mapLessonProgramToLessonProgramResponse(LessonProgram lessonProgram){
        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .educationTerm(lessonProgram.getEducationTerm())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLessons())
                .build();
    }


}
