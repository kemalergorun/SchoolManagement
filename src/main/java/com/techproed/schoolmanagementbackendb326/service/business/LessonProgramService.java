package com.techproed.schoolmanagementbackendb326.service.business;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonProgramRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonProgramResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.business.LessonProgramRepository;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final TimeValidator timeValidator;

    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@Valid LessonProgramRequest lessonProgramRequest) {

        Set<Lesson>lessons=lessonService.getAllByIdSet(lessonProgramRequest.getLessonIdList());

        EducationTerm educationTerm=educationTermService.isEducationTermExist(lessonProgramRequest.getEducationTermId());

        timeValidator.checkStartIsBeforeStop(lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime());






    }
}
