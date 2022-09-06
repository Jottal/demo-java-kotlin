package demo.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Document("relationships")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Relationship {

    @Id
    private String id;

    @DBRef
    private User follower;

    @DBRef
    private User followed;
}
