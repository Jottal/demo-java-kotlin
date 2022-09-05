package demo.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String keyName;

    private String name;
    private String password;
    private String nickName;
    private String bio;
    private String birthday;
    private String photo;

    public User(String name, String email, String password, String nickName, String keyName, String bio,
            String birthday, String photo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.keyName = keyName;
        this.bio = bio;
        this.birthday = birthday;
        this.photo = photo;
    }
}
