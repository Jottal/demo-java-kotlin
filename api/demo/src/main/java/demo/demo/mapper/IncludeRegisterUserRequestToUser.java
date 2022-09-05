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
        User user = modelMapper.map(registerUserRequest, User.class);
        user.setBio("Your bio is here.");
        user.setPhoto(
                "https://media.istockphoto.com/vectors/default-profile-picture-avatar-photo-placeholder-vector-illustration-vector-id1223671392?k=20&m=1223671392&s=170667a&w=0&h=kEAA35Eaz8k8A3qAGkuY8OZxpfvn9653gDjQwDHZGPE=");
        return user;
    }
}
