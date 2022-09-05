package demo.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.demo.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByKeyName(String keyName);
}
