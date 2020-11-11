package controllers.messages;

import dao.impl.MySQLTwitterDAO;
import dao.impl.MySQLUserDAO;
import model.AppUser;
import model.Tweet;
import services.AppUserService;
import services.TweetService;
import services.impl.AppUserServiceImpl;
import services.impl.TweetServiceImpl;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "Messages", value = "/messages")
public class MessagesServlet extends HttpServlet {

    private TweetService tweetService;
    private AppUserService appUserService;

    @Override
    public void init() throws ServletException {
        tweetService = new TweetServiceImpl(new MySQLTwitterDAO());
        appUserService = new AppUserServiceImpl(new MySQLUserDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppUser user = appUserService.getUserByLogin(ServletUtils.getUserLoginFromSession(req));
        Set<AppUser> followed = user.getFollowing();
        List<Tweet> allTweets = followed.stream()
                .flatMap(u -> tweetService.getUserTweets(u).stream())
                .collect(Collectors.toList());

        allTweets.addAll(tweetService.getUserTweets(user));

        List<Tweet> allTweetsSorted = allTweets.stream()
                .sorted((t1, t2) -> t2.getPublishedAt().compareTo(t1.getPublishedAt()))
                .collect(Collectors.toList());

        req.setAttribute(ServletUtils.FOLLOWED_TWEETS, allTweetsSorted);
        req.getRequestDispatcher("/messages.jsp").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req, resp);
    }
}
