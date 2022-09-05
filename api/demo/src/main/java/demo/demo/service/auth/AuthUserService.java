package demo.demo.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.demo.controller.request.LoginUserRequest;
import demo.demo.controller.request.RegisterUserRequest;
import demo.demo.controller.response.auth.LoginUserResponse;
import demo.demo.domain.User;
import demo.demo.exception.BusinessValidationException;
import demo.demo.exception.NotFoundException;
import demo.demo.mapper.IncludeLoginUserRequesttoLoginUserResponse;
import demo.demo.mapper.IncludeRegisterUserRequestToUser;
import demo.demo.repository.UserRepository;
import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    IncludeRegisterUserRequestToUser includeRegisterUserRequestToUser;

    @Autowired
    IncludeLoginUserRequesttoLoginUserResponse includeLoginUserRequesttoLoginUserResponse;

    @Autowired
    UserRepository userRepository;

    public void register(RegisterUserRequest registerUserRequest) {

        userRepository.findByEmail(registerUserRequest.getEmail()).ifPresent(u -> {
            throw new BusinessValidationException("Email already exists");
        });

        userRepository.findByKeyName(registerUserRequest.getKeyName()).ifPresent(u -> {
            throw new BusinessValidationException("KeyName already exists");
        });

        User user = includeRegisterUserRequestToUser.apply(registerUserRequest);
        userRepository.save(user);
    }

    public LoginUserResponse login(LoginUserRequest loginUserRequest) {
        Optional<User> user = userRepository.findByEmail(loginUserRequest.getEmail());

        if (!user.isPresent()) {
            throw new NotFoundException("Email not found");
        }

        if (!user.get().getPassword().equals(loginUserRequest.getPassword())) {
            throw new BusinessValidationException("Password is incorrect");
        }

        return includeLoginUserRequesttoLoginUserResponse.apply(user.get());
    }
}
