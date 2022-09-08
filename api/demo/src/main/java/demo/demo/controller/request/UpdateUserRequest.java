package demo.demo.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String id;
    private String name;
    private String email;
    private String nickName;
    private String keyName;
    private String bio;
    private String photo;
}
