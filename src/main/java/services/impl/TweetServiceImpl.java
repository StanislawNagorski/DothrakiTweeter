package services.impl;

import dao.TweetDAO;
import model.AppUser;
import model.Tweet;
import services.TweetService;

import java.util.List;

public class TweetServiceImpl implements TweetService {

    TweetDAO tweetDAO;

    public TweetServiceImpl(TweetDAO tweetDAO) {
        this.tweetDAO = tweetDAO;
    }

    @Override
    public List<Tweet> getUserTweets(AppUser user) {
        return tweetDAO.getUserTweets(user);
    }

    @Override
    public void addTweet(String author, String message) {
        Tweet tweet = new Tweet(author, message);
        tweetDAO.saveTweet(tweet);
    }

    @Override
    public void deleteTweet(Long tweetID) {
        tweetDAO.deleteTweet(tweetID);
    }
}
