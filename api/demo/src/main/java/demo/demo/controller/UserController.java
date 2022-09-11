package demo.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.controller.request.LoginUserRequest;
import demo.demo.controller.request.RegisterUserRequest;
import demo.demo.controller.request.UpdateUserRequest;
import demo.demo.controller.response.UserResponse;
import demo.demo.controller.response.auth.LoginUserResponse;
import demo.demo.service.UserService;
import demo.demo.service.auth.AuthUserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthUserService authUserService;

    @Autowired
    UserService userService;

    @PostMapping("/public/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        authUserService.register(registerUserRequest);
    }

    @PostMapping("/public/login")
    @ResponseStatus(code = HttpStatus.OK)
    public LoginUserResponse loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        return authUserService.login(loginUserRequest);
    }

    @GetMapping("/private/{filter}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse getUser(@PathVariable("filter") String filter) {
        return userService.getUser(filter);
    }

    @PutMapping("/private/update")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        userService.updateUser(updateUserRequest);
    }

    @PostMapping("/private/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/private/refresh/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserResponse> getAllUser(@PathVariable("id") String id) {
        return userService.getAllUser(id);
    }
}
