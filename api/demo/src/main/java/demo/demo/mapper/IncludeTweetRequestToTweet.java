package demo.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.demo.controller.request.CreateTweetRequest;
import demo.demo.domain.Tweet;
import demo.demo.domain.User;

@Component
public class IncludeTweetRequestToTweet {

    @Autowired
    private ModelMapper modelMapper;

    public Tweet apply(CreateTweetRequest createTweetRequest, User author) {
        Tweet tweet = modelMapper.map(createTweetRequest, Tweet.class);
        tweet.setAuthor(author);
        return tweet;
    }
}
