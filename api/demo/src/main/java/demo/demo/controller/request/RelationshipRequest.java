package demo.demo.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationshipRequest {
    private String followerId;
    private String followedId;
}
