package controllers.users;

import dao.impl.MySQLUserDAO;
import model.AppUser;
import services.AppUserService;
import services.impl.AppUserServiceImpl;
import utils.UserServletProperties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

import static utils.ServletUtils.*;
import static utils.UserServletProperties.*;

@WebServlet (name = "Users", value = "/users")
public class UsersServlet extends HttpServlet {

    AppUserService service;

    @Override
    public void init() throws ServletException {
        service = new AppUserServiceImpl(new MySQLUserDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLogin = getUserLoginFromSession(req);
        AppUser user = service.getUserByLogin(userLogin);

        HashSet<AppUser> notFollowed = service.getNotFollowed(user)
                .stream()
                .sorted((o1, o2) -> o2.getFollowers().size()-o1.getFollowers().size())
                .limit(DEFAULT_TOP_USERS_TO_FOLLOW)
                .collect(Collectors.toCollection(HashSet::new));

        HashSet<AppUser> followedUsers = service.getFollowedUsers(user);
        int numberOfFollowing = followedUsers.size();
        int numberOfPagesForFollowing = (int) Math.ceil(numberOfFollowing / DEFAULT_USERS_LIMIT);

        if (req.getParameter(FOLLOWING_CURRENT_PAGE) != null) {
            int page = Integer.parseInt(req.getParameter(FOLLOWING_CURRENT_PAGE));
            if (page <= 0){page = 0;}
            if (page > numberOfPagesForFollowing) {page = numberOfPagesForFollowing;}
            followedUsers = service.getFollowedUsers(user, (page* DEFAULT_USERS_LIMIT), DEFAULT_USERS_LIMIT);
            req.setAttribute(FOLLOWING_CURRENT_PAGE, page);

        } else {
            followedUsers = service.getFollowedUsers(user, DEFAULT_USERS_OFFSET, DEFAULT_USERS_LIMIT);
        }
        req.setAttribute(FOLLOWING_NUMBER_OF_PAGES, numberOfPagesForFollowing);


        HashSet<AppUser> followers;
        int numberOfFollowers = service.getFollowers(user).size();
        int numberOfPages = (int) Math.ceil(numberOfFollowers / DEFAULT_USERS_LIMIT);

        if (req.getParameter(FOLLOWERS_CURRENT_PAGE) != null) {
            int page = Integer.parseInt(req.getParameter(FOLLOWERS_CURRENT_PAGE));
            if (page <= 0){page = 0;}
            if (page > numberOfPages) {page = numberOfPages;}
            followers = service.getFollowers(user, (page* DEFAULT_USERS_LIMIT), DEFAULT_USERS_LIMIT);
            req.setAttribute(FOLLOWERS_CURRENT_PAGE, page);

        } else {
            followers = service.getFollowers(user, DEFAULT_USERS_OFFSET, DEFAULT_USERS_LIMIT);
        }
        req.setAttribute(FOLLOWERS_NUMBER_OF_PAGES, numberOfPages);

        req.setAttribute(FOLLOWED_USERS, followedUsers);
        req.setAttribute(NOT_FOLLOWED_USERS, notFollowed);
        req.setAttribute(FOLLOWERS, followers);

        req.getRequestDispatcher("/users.jsp").forward(req, resp);

    }
}
