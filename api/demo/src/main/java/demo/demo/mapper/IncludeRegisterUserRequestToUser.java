package demo.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.demo.controller.request.RegisterUserRequest;
import demo.demo.domain.User;

@Component
public class IncludeRegisterUserRequestToUser {
    @Autowired
    private ModelMapper modelMapper;

    public User apply(RegisterUserRequest registerUserRequest) {
        return modelMapper.map(registerUserRequest, User.class);
    }
}
