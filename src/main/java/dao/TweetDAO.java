package dao;

import model.AppUser;
import model.Tweet;

import java.util.List;
import java.util.Optional;

public interface TweetDAO {


    Optional<Tweet> getTweet(Long id);

    void saveTweet(Tweet tweet);

    void deleteTweet(Long id);

    List<Tweet> getUserTweets(AppUser user);


}
