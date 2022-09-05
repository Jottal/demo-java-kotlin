package demo.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.demo.controller.request.UpdateUserRequest;
import demo.demo.domain.User;

@Component
public class IncludeUpdateUserRequestToUser {
    @Autowired
    private ModelMapper modelMapper;

    public User apply(UpdateUserRequest updateUserRequest) {
        User user = modelMapper.map(updateUserRequest, User.class);
        return user;
    }
}
