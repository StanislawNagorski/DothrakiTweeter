package services.impl;

import dao.AppUserDAO;
import dao.TweetDAO;
import errors.ValidationError;
import model.AppUser;
import org.apache.commons.codec.digest.DigestUtils;
import services.AppUserService;
import utils.ServletUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static utils.ServletUtils.*;

public class AppUserServiceImpl implements AppUserService {

    private final AppUserDAO appUserDAO;

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
    public Optional<ValidationError> validateLogin(String login) {
        if (isUserLoginNoneAvailable(login)){
            return Optional.of(new ValidationError(LOGIN_ERROR_HEADER, LOGIN_IN_USE_ERROR_MESSAGE));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ValidationError> validateEmail(String email) {
        if (isUserEmailNoneAvailable(email)){
            return Optional.of(new ValidationError(EMAIL_ERROR_HEADER, EMAIL_ERROR_MESSAGE));
        }
        return Optional.empty();
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
    public void deactivate(AppUser user) {
        appUserDAO.deleteUser(user);
    }

    @Override
    public List<ValidationError> loginValidation(String login, String password) {
        List<ValidationError> errors = new ArrayList<>();

        if (!isLoginAndPasswordValid(login, password)){
            errors.add(new ValidationError(PASSWORD_ERROR_HEADER, WRONG_PASSWORD_ERROR_MESSAGE));
        }
        if (isUserNoneActive(login)){
            errors.add(new ValidationError(USER_INACTIVE_ERROR_HEADER, USER_INACTIVE_ERROR_MESSAGE));
        }
        return errors;
    }


    private boolean isUserNoneActive(String login) {
        Optional<AppUser> userByLogin = appUserDAO.getUserByLogin(login);
        if (userByLogin.isEmpty()) {
            return false;
        }
        return !userByLogin.get().isActive();
    }

        private boolean isLoginAndPasswordValid(String login, String hashedPassword) {
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
    public HashSet<AppUser> getFollowers(AppUser loggedUser, int offset, int limit) {
        return appUserDAO.getFollowers(loggedUser, offset,limit);
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
    public HashSet<AppUser> getFollowedUsers(AppUser loggedUser, int offset, int limit) {
        return appUserDAO.getFollowedUsers(loggedUser,offset,limit);
    }

    @Override
    public void followUser(AppUser loggedUser, AppUser userToFollow) {
        appUserDAO.follow(loggedUser,userToFollow);
    }

    @Override
    public void unfollowUser(AppUser loggedUser, AppUser userToFollow) {
        appUserDAO.unfollow(loggedUser,userToFollow);
    }

    @Override
    public void changeLogin(AppUser appUser, String newLogin) {
        appUserDAO.setLogin(appUser, newLogin);
    }

    @Override
    public void changeName(AppUser appUser, String newName) {
      appUserDAO.setName(appUser,newName);
    }

    @Override
    public void changeLastName(AppUser appUser, String newLastName) {
       appUserDAO.setLastName(appUser,newLastName);
    }

    @Override
    public void changeEmail(AppUser appUser, String newEmail) {
        appUserDAO.setEmail(appUser,newEmail);
    }

    @Override
    public void changePassword(AppUser appUser, String newPassword) {
        appUserDAO.setPassword(appUser,newPassword);
    }

    @Override
    public void changeAvatar(AppUser appUser, String avatarPath) {
        appUserDAO.setAvatar(appUser, avatarPath);
    }


}
