package controllers.messages;

import dao.impl.MySQLTwitterDAO;
import org.apache.commons.codec.digest.DigestUtils;
import services.TweetService;
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

    @Override
    public void init() throws ServletException {
        tweetService = new TweetServiceImpl(new MySQLTwitterDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long tweetIdFromParameter = ServletUtils.getTweetIdFromParameter(req);
        tweetService.deleteTweet(tweetIdFromParameter);
        req.getRequestDispatcher("messages").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

}
