package com.geekzone.model.tweet;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tweetId;

    private String tweetTitle;

    private String tweetContent;

    private Date tweetCreated;

    public Tweet() {
    }

    public Tweet(String tweetTitle, String tweetContent, Date tweetCreated) {
        this.tweetId = tweetId;
        this.tweetTitle = tweetTitle;
        this.tweetContent = tweetContent;
        this.tweetCreated = tweetCreated;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetTitle() {
        return tweetTitle;
    }

    public void setTweetTitle(String tweetTitle) {
        this.tweetTitle = tweetTitle;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public Date getTweetCreated() {
        return tweetCreated;
    }

    public void setTweetCreated(Date tweetCreated) {
        this.tweetCreated = tweetCreated;
    }
    
    
    
    
}
