package demo.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.controller.request.RegisterUserRequest;
import demo.demo.service.auth.RegisterUserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    RegisterUserService registerUserService;

    @PostMapping("/public/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        registerUserService.register(registerUserRequest);
    }
}
