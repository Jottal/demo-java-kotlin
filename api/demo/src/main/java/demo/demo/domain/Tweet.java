package demo.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Document("tweets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tweet {

    @Id
    private String id;

    private User author;
    private String content;
    private String img;
    private String replyedTweetId;
    private String retweetTweetId;

    public Tweet(User author, String content, String img, String replyedTweetId, String retweetTweetId) {
        this.author = author;
        this.content = content;
        this.img = img;
        this.replyedTweetId = replyedTweetId;
        this.retweetTweetId = retweetTweetId;
    }
}
