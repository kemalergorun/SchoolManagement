package com.techproed.schoolmanagementbackendb326.service.helper;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.exception.BadRequestException;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

  private final UserRepository userRepository;


  public User isUserExist(Long id) {
    return userRepository.findById(id)
        .orElseThrow(()->new ResourceNotFoundException(
            String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
  }

  public void checkisAdvisor(User user){
    if (!user.getIsAdvisor()){
      throw new BadRequestException(String.format(ErrorMessages.NOT_FOUND_ADVISOR_MESSAGE,user.getUsername()));
    }
  }

  public void checkBuildIn(User user) {
    if(user.getBuildIn()){
      throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
    }
  }

  public User loadByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if(user == null){
      throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, username));
    }
    return user;
  }
  public void checkUserRole (User user, RoleType roleType) {
    if(!user.getUserRole().getRoleType().equals(roleType)){
      throw new BadRequestException(ErrorMessages.NOT_HAVE_EXPECTED_ROLE_USER);
    }
  }
  public void checkIsAdvisor(User user) {
    if(!user.getIsAdvisor()){
      throw new BadRequestException(String.format(ErrorMessages.NOT_ADVISOR_TEACHER_MESSAGE, user.getUsername()));
    }
  }





}
