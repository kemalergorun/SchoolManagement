package com.techproed.schoolmanagementbackendb326.service.user;

import com.techproed.schoolmanagementbackendb326.payload.request.user.UserRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.user.UserResponse;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserService {
    public ResponseMessage<UserResponse> saveUser(@Valid UserRequest userRequest, String userRole) {
        //validate unique prop.

    }
}
