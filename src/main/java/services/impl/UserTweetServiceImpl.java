package services.impl;

import dao.AppUserDAO;
import services.AppUserService;
import services.TweetService;

public class UserTweetServiceImpl {

    private AppUserService appUserService;
    private TweetService tweetService;

    public UserTweetServiceImpl(AppUserService appUserService, TweetService tweetService) {
        this.appUserService = appUserService;
        this.tweetService = tweetService;
    }
}
