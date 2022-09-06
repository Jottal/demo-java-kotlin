package demo.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.demo.controller.request.RelationshipRequest;
import demo.demo.controller.response.UserResponse;
import demo.demo.domain.Relationship;
import demo.demo.domain.User;
import demo.demo.exception.BusinessValidationException;
import demo.demo.mapper.IncludeFollowerAndFollowedToRelationship;
import demo.demo.mapper.IncludeFollowerRelationshipToUserResponse;
import demo.demo.repository.RelationshipRepository;
import demo.demo.repository.UserRepository;

@Service
public class RelationshipService {

    @Autowired
    IncludeFollowerRelationshipToUserResponse includeFollowerRelationshipToUserResponse;

    @Autowired
    IncludeFollowerAndFollowedToRelationship includeFollowerAndFollowedToRelationship;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationshipRepository relationshipRepository;

    public void createRelationship(RelationshipRequest createRelationshipRequest) {
        User follower = userRepository.findById(createRelationshipRequest.getFollowerId()).get();
        User followed = userRepository.findById(createRelationshipRequest.getFollowedId()).get();

        Optional<Relationship> relationshipExists = relationshipRepository.findByFollowerIdAndFollowedId(
                follower.getId(),
                followed.getId());

        if (relationshipExists.isPresent()) {
            throw new BusinessValidationException("Relationship already exists");
        }

        Relationship relationship = includeFollowerAndFollowedToRelationship.apply(follower, followed);
        relationshipRepository.save(relationship);
    }

    public Boolean isFollowing(RelationshipRequest createRelationshipRequest) {
        User follower = userRepository.findById(createRelationshipRequest.getFollowerId()).get();
        User followed = userRepository.findById(createRelationshipRequest.getFollowedId()).get();

        Optional<Relationship> relationshipExists = relationshipRepository.findByFollowerIdAndFollowedId(
                follower.getId(),
                followed.getId());

        return relationshipExists.isPresent();
    }

    public List<UserResponse> getFollowers(String id) {
        User user = userRepository.findById(id).get();
        List<Relationship> relationships = relationshipRepository.findByFollowedId(user.getId());

        List<UserResponse> userResponses = relationships.stream().map(includeFollowerRelationshipToUserResponse::apply)
                .collect(Collectors.toList());
        return userResponses;
    }

    public List<UserResponse> getFollowed(String id) {
        User user = userRepository.findById(id).get();
        List<Relationship> relationships = relationshipRepository.findByFollowerId(user.getId());

        List<UserResponse> userResponses = relationships.stream().map(includeFollowerRelationshipToUserResponse::apply)
                .collect(Collectors.toList());
        return userResponses;
    }

    public void deleteRelationship(RelationshipRequest createRelationshipRequest) {
        User follower = userRepository.findById(createRelationshipRequest.getFollowerId()).get();
        User followed = userRepository.findById(createRelationshipRequest.getFollowedId()).get();

        Optional<Relationship> relationshipExists = relationshipRepository.findByFollowerIdAndFollowedId(
                follower.getId(),
                followed.getId());

        if (!relationshipExists.isPresent()) {
            throw new BusinessValidationException("Relationship does not exists");
        }

        relationshipRepository.delete(relationshipExists.get());
    }
}
