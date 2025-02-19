package com.techproed.schoolmanagementbackendb326.service.business;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.exception.ConflictException;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.LessonMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.business.LessonRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonService {
    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;
    private final PageableHelper pageableHelper;



    public ResponseMessage<LessonResponse> saveLesson(@Valid LessonRequest lessonRequest) {
        isLessonExistByName(lessonRequest.getLessonName());
        Lesson lesson=lessonMapper.mapLessonRequestToLesson(lessonRequest);
        Lesson savedLesson=lessonRepository.save(lesson);

        return ResponseMessage.<LessonResponse>builder()
                .returnBody(lessonMapper.mapLessonToLessonResponse(savedLesson))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.LESSON_SAVE)
                .build();

    }
    private void isLessonExistByName(String lessonName){
        if (lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName).isPresent()){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE,lessonName));
        }





        }

    public Page<LessonResponse> getLessonByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, type);

        Page<Lesson> lessons = lessonRepository.findAll(pageable);
// use mapper
        return lessons.map(lessonMapper::mapLessonToLessonResponse);







    }
    public Lesson isLessonExistById(Long lessonId){
        return lessonRepository.findById(lessonId)
                .orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE,lessonId)));
    }

    public LessonResponse updateLesson(@Valid LessonRequest lessonRequest, Long lessonId) {
        Lesson lesson=isLessonExistById(lessonId);
        if (!lessonRequest.getLessonName().equals(lesson.getLessonName())){
            isLessonExistByName(lessonRequest.getLessonName());
        }
        Lesson updatedLesson=lessonMapper.mapLessonRequestToLesson(lessonRequest);
        updatedLesson.setId(lessonId);
        Lesson savedLesson=lessonRepository.save(updatedLesson);
        return lessonMapper.mapLessonToLessonResponse(savedLesson);
    }


    public Set<Lesson> getAllByIdSet(Set<Long> idSet) {
        return idSet.stream()
                .map(this::isLessonExistById)
                .collect(Collectors.toSet());
    }
}
