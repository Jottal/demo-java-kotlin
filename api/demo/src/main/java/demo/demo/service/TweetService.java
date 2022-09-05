package demo.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.demo.controller.request.CreateTweetRequest;
import demo.demo.domain.Tweet;
import demo.demo.domain.User;
import demo.demo.exception.NotFoundException;
import demo.demo.mapper.IncludeTweetRequestToTweet;
import demo.demo.repository.TweetRepository;
import demo.demo.repository.UserRepository;

@Service
public class TweetService {

    @Autowired
    IncludeTweetRequestToTweet includeTweetRequestToTweet;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    public void createTweet(CreateTweetRequest createTweetRequest) {

        Optional<User> author = userRepository.findById(createTweetRequest.getAuthorId());

        if (!author.isPresent()) {
            throw new NotFoundException("User not found");
        }

        Tweet tweet = includeTweetRequestToTweet.apply(createTweetRequest, author.get());
        tweetRepository.save(tweet);
    }
}
