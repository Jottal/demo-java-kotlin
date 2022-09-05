package demo.demo.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.demo.controller.request.RegisterUserRequest;
import demo.demo.domain.User;
import demo.demo.mapper.IncludeRegisterUserRequestToUser;
import demo.demo.repository.UserRepository;

@Service
public class RegisterUserService {

    @Autowired
    IncludeRegisterUserRequestToUser includeRegisterUserRequestToUser;

    @Autowired
    UserRepository userRepository;

    public void register(RegisterUserRequest registerUserRequest) {
        User user = includeRegisterUserRequestToUser.apply(registerUserRequest);

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already exists");
        });

        userRepository.findByKeyName(user.getKeyName()).ifPresent(u -> {
            throw new RuntimeException("KeyName already exists");
        });

        userRepository.save(user);
    }
}
