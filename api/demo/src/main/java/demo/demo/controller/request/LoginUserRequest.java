package demo.demo.controller.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
