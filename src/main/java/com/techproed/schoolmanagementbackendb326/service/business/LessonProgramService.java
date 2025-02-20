package com.techproed.schoolmanagementbackendb326.service.business;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import com.techproed.schoolmanagementbackendb326.payload.mappers.LessonProgramMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonProgramRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonProgramResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.business.LessonProgramRepository;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final TimeValidator timeValidator;

    private final LessonProgramMapper lessonProgramMapper;
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@Valid LessonProgramRequest lessonProgramRequest) {

        List<Lesson> lessons=lessonService.getAllByIdSet(lessonProgramRequest.getLessonIdList());

        EducationTerm educationTerm=educationTermService.isEducationTermExist(lessonProgramRequest.getEducationTermId());

        timeValidator.checkStartIsBeforeStop(lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime());

        LessonProgram lessonProgramToSave=lessonProgramMapper.mapLessonProgramRequestToLessonProgram(
                lessonProgramRequest,lessons,educationTerm);
        LessonProgram savedLessonProgram=lessonProgramRepository.save(lessonProgramToSave);
        return ResponseMessage.<LessonProgramResponse>builder()
                .returnBody(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.LESSON_PROGRAM_SAVE)
                .build();






    }

    public List<LessonProgramResponse> getAllUnassigned(LessonProgramService lessonProgramService) {
        return lessonProgramRepository.findByUsers_IdNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());




    }

    public List<LessonProgramResponse> getAllAssigned() {
        return lessonProgramRepository.findByUsers_IdNotNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());





    }

    public List<LessonProgramResponse> getAllLessonPrograms() {
        List<LessonProgram> allLessonPrograms = lessonProgramRepository.findAll();
        return allLessonPrograms.stream().map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse).collect(Collectors.toList());
    }

}
