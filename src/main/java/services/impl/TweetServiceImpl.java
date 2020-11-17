package services.impl;

import dao.TweetDAO;
import message_decorators.DothrakiTranslator;
import model.AppUser;
import model.Tweet;
import services.TweetService;

import java.util.List;

public class TweetServiceImpl implements TweetService {

    TweetDAO tweetDAO;
    private final DothrakiTranslator translator = new DothrakiTranslator();

    public TweetServiceImpl(TweetDAO tweetDAO) {
        this.tweetDAO = tweetDAO;
    }

    @Override
    public List<Tweet> getUserTweets(AppUser user) {
        return tweetDAO.getUserTweets(user);
    }

    @Override
    public void addTweet(AppUser author, String message) {
        Tweet tweet = new Tweet(author, message);
        tweet.setTranslation(translator.translate(message));
        tweetDAO.saveTweet(tweet);
    }



    @Override
    public void deleteTweet(Long tweetID) {
        tweetDAO.deleteTweet(tweetID);
    }
}
