package demo.demo.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTweetRequest {
    private String authorId;
    private String content;
    private String img;
    private String replyedTweetId;
    private String retweetTweetId;
}
