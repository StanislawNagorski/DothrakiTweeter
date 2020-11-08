package services.impl;

import dao.TweetDAO;
import message_decorators.MessageDecorator;
import model.AppUser;
import model.Tweet;
import services.TweetService;

import java.util.List;

public class TweetServiceImpl implements TweetService {

    TweetDAO tweetDAO;
    private final MessageDecorator md = new MessageDecorator();

    public TweetServiceImpl(TweetDAO tweetDAO) {
        this.tweetDAO = tweetDAO;
    }

    @Override
    public List<Tweet> getUserTweets(AppUser user) {
        return tweetDAO.getUserTweets(user);
    }

    @Override
    public void addTweet(AppUser author, String message) {
        Tweet tweet = new Tweet(author, modifyMessage(message));
        tweetDAO.saveTweet(tweet);
    }

    private String modifyMessage(String message){
        return md.decor(message);
    }

    @Override
    public void deleteTweet(Long tweetID) {
        tweetDAO.deleteTweet(tweetID);
    }
}
