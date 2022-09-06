package demo.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.demo.controller.response.UserResponse;
import demo.demo.domain.Relationship;

@Component
public class IncludeFollowerRelationshipToUserResponse {

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse apply(Relationship relationship) {
        return modelMapper.map(relationship.getFollower(), UserResponse.class);
    }
}
