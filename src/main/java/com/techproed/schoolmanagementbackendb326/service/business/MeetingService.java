package com.techproed.schoolmanagementbackendb326.service.business;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.payload.mappers.MeetingMapper;
import com.techproed.schoolmanagementbackendb326.payload.request.business.MeetingRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.MeetingResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.business.MeetingRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final TimeValidator timeValidator;
    private final MeetingMapper meetingMapper;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;





    public ResponseMessage<MeetingResponse> save(HttpServletRequest httpServletRequest, @Valid MeetingRequest meetingRequest) {
        String username=(String) httpServletRequest.getAttribute("username");
        User teacher=methodHelper.loadByUsername(username);
        methodHelper.checkisAdvisor(teacher);
        timeValidator.checkStartIsBeforeStop(
                meetingRequest.getStartTime(),meetingRequest.getStopTime());






    }
}
