package com.techproed.schoolmanagementbackendb326.service.user;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.payload.mappers.UserMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.AddLessonProgramForStudent;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentUpdateRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.user.StudentResponse;
import com.techproed.schoolmanagementbackendb326.repository.user.UserRepository;
import com.techproed.schoolmanagementbackendb326.service.business.LessonProgramService;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import com.techproed.schoolmanagementbackendb326.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final MethodHelper methodHelper;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final LessonProgramService lessonProgramService;
    private final TimeValidator timeValidator;



    public ResponseMessage<StudentResponse> save(StudentRequest studentRequest) {

        User advisorTeacher=methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());
        methodHelper.checkisAdvisor(advisorTeacher);
        uniquePropertyValidator.checkDuplication(
                studentRequest.getUsername(),
                studentRequest.getSsn(),
                studentRequest.getPhoneNumber(),
                studentRequest.getEmail());

        User student=userMapper.mapUserRequestToUser(studentRequest, RoleType.STUDENT.getName());
        student.setAdvisorTeacherId(advisorTeacher.getId());
        student.setActive(true);
        student.setBuildIn(false);
        student.setStudentNumber(getLastStudentNumber());
        User savedStudent=userRepository.save(student);
        return ResponseMessage.<StudentResponse>
                builder()
                .returnBody(userMapper.mapUserToStudentResponse(savedStudent))
                .message(SuccessMessages.STUDENT_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }




    private int getLastStudentNumber(){
        if (!userRepository.findStudent()){
            return 1000;

        }
        return userRepository.getMaxStudentNumber()+1;

    }
    public String updateStudent(HttpServletRequest httpServletRequest,
                                StudentUpdateRequest studentUpdateRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        User student = methodHelper.loadByUsername(username);
        uniquePropertyValidator.checkUniqueProperty(student, studentUpdateRequest);
        User userToUpdate = userMapper.mapStudentUpdateRequestToUser(studentUpdateRequest);
        userToUpdate.setId(student.getId());
        userToUpdate.setPassword(student.getPassword());
        userToUpdate.setBuildIn(student.getBuildIn());
        userToUpdate.setAdvisorTeacherId(student.getAdvisorTeacherId());
        userRepository.save(userToUpdate);
        return SuccessMessages.STUDENT_UPDATE;
    }

    public ResponseMessage<StudentResponse> updateStudentByManager(Long studentId, @Valid StudentRequest studentRequest) {

        User student=methodHelper.isUserExist(studentId);
        methodHelper.checkUserRole(student,RoleType.STUDENT);
        uniquePropertyValidator.checkUniqueProperty(student,studentRequest);
        User studentToUpdate=userMapper.mapUserRequestToUser(studentRequest,RoleType.STUDENT.getName());

        studentToUpdate.setId(student.getId());
        studentToUpdate.setPassword(student.getPassword());
        studentToUpdate.setBuildIn(student.getBuildIn());
        studentToUpdate.setAdvisorTeacherId(student.getAdvisorTeacherId());
        studentToUpdate.setStudentNumber(student.getStudentNumber());
        return  ResponseMessage.<StudentResponse>builder()
                .message(SuccessMessages.STUDENT_UPDATE)
                .returnBody(userMapper.mapUserToStudentResponse(userRepository.save(studentToUpdate)))
                .httpStatus(HttpStatus.OK)
                .build();






    }


    public ResponseMessage<StudentResponse> addLessonProgram(HttpServletRequest httpServletRequest, @Valid AddLessonProgramForStudent addLessonProgramForStudent) {
        String username=(String) httpServletRequest.getAttribute("username");
        User loggedInUser=methodHelper.loadByUsername(username);
        List<LessonProgram>lessonProgramFromDTO=
                lessonProgramService.getLessonProgramById(addLessonProgramForStudent.getLessonProgramId());
        List<LessonProgram>studentLessonProgram=loggedInUser.getLessonProgramList();
        studentLessonProgram.addAll(lessonProgramFromDTO);



    }
}
