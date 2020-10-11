package services;

import model.AppUser;
import model.Tweet;

import java.util.List;

public interface TweetService {

    List<Tweet> getUserTweets(AppUser user);
    void addTweet(String author, String message);
    void deleteTweet(Long tweetId);

}
