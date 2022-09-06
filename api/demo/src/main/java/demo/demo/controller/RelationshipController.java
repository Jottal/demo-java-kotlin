package demo.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.controller.request.CreateRelationshipRequest;
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
    public void createRelationship(@RequestBody @Valid CreateRelationshipRequest createRelationshipRequest) {
        relationshipService.createRelationship(createRelationshipRequest);
    }

    // @GetMapping("/private/followers/{id}")
    // public List<UserResponse> getFollowers(@PathVariable String id) {
    // List<Relationship> relationships =
    // relationshipRepository.findByFollowerId(id);
    // }
}
