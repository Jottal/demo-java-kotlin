package demo.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.demo.domain.Tweet;

public interface TweetRepository extends MongoRepository<Tweet, String> {

}
