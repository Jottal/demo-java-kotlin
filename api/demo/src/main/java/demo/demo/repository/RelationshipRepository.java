package demo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.demo.domain.Relationship;

public interface RelationshipRepository extends MongoRepository<Relationship, String> {
    List<Relationship> findByFollowerId(String followerId);

    Optional<Relationship> findByFollowerIdAndFollowedId(String followerId, String followedId);
}
