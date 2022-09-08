package demo.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.demo.controller.request.UpdateUserRequest;
import demo.demo.controller.response.UserResponse;
import demo.demo.domain.User;
import demo.demo.exception.BusinessValidationException;
import demo.demo.exception.NotFoundException;
import demo.demo.mapper.IncludeUpdateUserRequestToUser;
import demo.demo.mapper.IncludeUserToUserResponse;
import demo.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    IncludeUserToUserResponse includeUserToUserResponse;

    @Autowired
    IncludeUpdateUserRequestToUser includeUpdateUserRequestToUser;

    @Autowired
    UserRepository userRepository;

    public UserResponse getUser(String filter) {

        Optional<User> userFindedById = userRepository.findById(filter);

        if (userFindedById.isPresent()) {
            UserResponse userResponse = includeUserToUserResponse.apply(userFindedById.get());
            return userResponse;
        }

        Optional<User> userFindedByKeyName = userRepository.findByKeyName(filter);

        if (userFindedByKeyName.isPresent()) {
            UserResponse userResponse = includeUserToUserResponse.apply(userFindedByKeyName.get());
            return userResponse;
        }

        Optional<User> userFindedByEmail = userRepository.findByEmail(filter);

        if (userFindedByEmail.isPresent()) {
            UserResponse userResponse = includeUserToUserResponse.apply(userFindedByEmail.get());
            return userResponse;
        }

        throw new NotFoundException("User not found");
    }

    public void updateUser(UpdateUserRequest updateUserRequest) {
        try {
            User user = includeUpdateUserRequestToUser.apply(updateUserRequest);
            userRepository.save(user);
        } catch (Exception e) {
            throw new BusinessValidationException(e.getMessage());
        }
    }

    public void deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }

        userRepository.delete(user.get());
    }

    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream().map(includeUserToUserResponse::apply)
                .collect(Collectors.toList());
        return userResponses;
    }
}
