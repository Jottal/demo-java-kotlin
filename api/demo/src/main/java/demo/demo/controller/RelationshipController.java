package demo.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.controller.request.RelationshipRequest;
import demo.demo.controller.response.UserResponse;
import demo.demo.repository.RelationshipRepository;
import demo.demo.repository.UserRepository;
import demo.demo.service.RelationshipService;

@RestController
@RequestMapping("/relationship")
public class RelationshipController {

    @Autowired
    RelationshipService relationshipService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationshipRepository relationshipRepository;

    @PostMapping("/private/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createRelationship(@RequestBody @Valid RelationshipRequest createRelationshipRequest) {
        relationshipService.createRelationship(createRelationshipRequest);
    }

    @PostMapping("/private/validate/following")
    @ResponseStatus(code = HttpStatus.OK)
    public Boolean isFollowing(@RequestBody @Valid RelationshipRequest createRelationshipRequest) {
        return relationshipService.isFollowing(createRelationshipRequest);
    }

    @GetMapping("/private/followers/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserResponse> getFollowers(@PathVariable String id) {
        return relationshipService.getFollowers(id);
    }

    @GetMapping("/private/followed/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserResponse> getFollowed(@PathVariable String id) {
        return relationshipService.getFollowed(id);
    }

    @PostMapping("/private/delete")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteRelationship(@RequestBody @Valid RelationshipRequest createRelationshipRequest) {
        relationshipService.deleteRelationship(createRelationshipRequest);
    }
}
