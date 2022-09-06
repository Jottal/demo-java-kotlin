package demo.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.demo.controller.request.CreateRelationshipRequest;
import demo.demo.domain.Relationship;
import demo.demo.domain.User;
import demo.demo.exception.BusinessValidationException;
import demo.demo.mapper.IncludeFollowerAndFollowedToRelationship;
import demo.demo.repository.RelationshipRepository;
import demo.demo.repository.UserRepository;

@Service
public class RelationshipService {

    @Autowired
    IncludeFollowerAndFollowedToRelationship includeFollowerAndFollowedToRelationship;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationshipRepository relationshipRepository;

    public void createRelationship(CreateRelationshipRequest createRelationshipRequest) {
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
}
