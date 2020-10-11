package services.impl;

import dao.AppUserDAO;
import dao.TweetDAO;
import errors.ValidationError;
import model.AppUser;
import org.apache.commons.codec.digest.DigestUtils;
import services.AppUserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static utils.ServletUtils.*;

public class AppUserServiceImpl implements AppUserService {

    private AppUserDAO appUserDAO;

    public AppUserServiceImpl(AppUserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    private boolean isUserLoginNoneAvailable(String login) {
        return appUserDAO.getUserByLogin(login).isPresent();
    }

    private boolean isUserEmailNoneAvailable(String email) {
        return appUserDAO.getUserByEmail(email).isPresent();
    }

    @Override
    public List<ValidationError> validateUser(String login, String email) {
        List<ValidationError> errors = new ArrayList<>();
        if (isUserEmailNoneAvailable(email)) {
            errors.add(new ValidationError(EMAIL_ERROR_HEADER, EMAIL_ERROR_MESSAGE));
        }
        if (isUserLoginNoneAvailable(login)) {
            errors.add(new ValidationError(LOGIN_ERROR_HEADER, LOGIN_IN_USE_ERROR_MESSAGE));
        }
        return errors;
    }

    @Override
    public void register(AppUser user) {
        appUserDAO.saveUser(user);
    }

    @Override
    public boolean isLoginAndPasswordValid(String login, String hashedPassword) {

        Optional<AppUser> userByLogin = appUserDAO.getUserByLogin(login);
        if (userByLogin.isEmpty()) {
            return false;
        }
        String passFromDB = userByLogin.get().getPassword();
        return passFromDB.equals(hashedPassword);
    }

    @Override
    public AppUser getUserByLogin(String login) {
        return appUserDAO.getUserByLogin(login).get();
    }

    @Override
    public HashSet<AppUser> getFollowers(AppUser loggedUser) {
        return appUserDAO.getFollowers(loggedUser);
    }

    @Override
    public HashSet<AppUser> getNotFollowed(AppUser loggedUSer) {
        return appUserDAO.getNotFollowed(loggedUSer);
    }

    @Override
    public HashSet<AppUser> getFollowedUsers(AppUser loggedUser) {
        return appUserDAO.getFollowedUsers(loggedUser);
    }

    @Override
    public void followUser(AppUser loggedUser, AppUser userToFollow) {
        appUserDAO.follow(loggedUser,userToFollow);
    }

    @Override
    public void unfollowUser(AppUser loggedUser, AppUser userToFollow) {
        appUserDAO.unfollow(loggedUser,userToFollow);
    }


}
