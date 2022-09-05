package demo.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.demo.controller.response.UserResponse;
import demo.demo.domain.User;

@Component
public class IncludeUserToUserResponse {
    @Autowired
    private ModelMapper modelMapper;

    public UserResponse apply(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
