package demo.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.controller.request.CreateTweetRequest;
import demo.demo.service.TweetService;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    TweetService tweetService;

    @PostMapping("/private/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createTweet(@RequestBody @Valid CreateTweetRequest createTweetRequest) {
        tweetService.createTweet(createTweetRequest);
    }
}
