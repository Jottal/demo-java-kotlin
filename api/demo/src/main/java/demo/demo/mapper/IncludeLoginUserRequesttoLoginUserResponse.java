package demo.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.demo.controller.response.auth.LoginUserResponse;
import demo.demo.domain.User;

@Component
public class IncludeLoginUserRequesttoLoginUserResponse {
    @Autowired
    private ModelMapper modelMapper;

    public LoginUserResponse apply(User user) {
        return modelMapper.map(user, LoginUserResponse.class);
    }
}
