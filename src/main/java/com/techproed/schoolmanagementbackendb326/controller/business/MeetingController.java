package com.techproed.schoolmanagementbackendb326.controller.business;

import com.techproed.schoolmanagementbackendb326.payload.request.business.MeetingRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.MeetingResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.business.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;


    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PostMapping("/save")
    public ResponseMessage<MeetingResponse>saveMeeting(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid MeetingRequest meetingRequest
            ){
        return meetingService.save(httpServletRequest,meetingRequest);

    }
}
