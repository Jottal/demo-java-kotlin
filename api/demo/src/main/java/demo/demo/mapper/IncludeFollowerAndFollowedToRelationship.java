package demo.demo.mapper;

import org.springframework.stereotype.Component;

import demo.demo.domain.Relationship;
import demo.demo.domain.User;

@Component
public class IncludeFollowerAndFollowedToRelationship {

    public Relationship apply(User follower, User followed) {
        Relationship relationship = new Relationship();
        relationship.setFollower(follower);
        relationship.setFollowed(followed);
        return relationship;
    }
}
