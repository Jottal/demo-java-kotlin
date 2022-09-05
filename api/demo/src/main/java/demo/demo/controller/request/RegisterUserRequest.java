package demo.demo.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;
    private String nickName;
    private String keyName;
    private String birthday;
}
