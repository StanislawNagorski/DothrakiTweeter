package controllers.messages;

import dao.impl.MySQLTwitterDAO;
import dao.impl.MySQLUserDAO;
import model.AppUser;
import org.apache.commons.codec.digest.DigestUtils;
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

@WebServlet (name = "DeleteTweet", value = "/deleteTweet")
public class DeleteTweetServlet extends HttpServlet {

    TweetService tweetService;
    AppUserService appUserService;

    @Override
    public void init() throws ServletException {
        tweetService = new TweetServiceImpl(new MySQLTwitterDAO());
        appUserService = new AppUserServiceImpl(new MySQLUserDAO());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long tweetIdFromParameter = ServletUtils.getTweetIdFromParameter(req);
        AppUser user = appUserService.getUserByLogin(ServletUtils.getUserLoginFromSession(req));

        tweetService.deleteTweet(user,tweetIdFromParameter);
        req.getRequestDispatcher("messages").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

}
