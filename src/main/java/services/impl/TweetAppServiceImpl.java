package services.impl;

import dao.AppUserDAO;
import dao.TweetDAO;
import dao.impl.MySQLTwitterDAO;
import dao.impl.MySQLUserDAO;
import errors.ValidationError;
import model.AppUser;
import services.TweetAppService;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static utils.ServletUtils.*;

public class TweetAppServiceImpl implements TweetAppService {

    private AppUserDAO appUserDAO;
    private TweetDAO tweetDAO;

    public TweetAppServiceImpl(AppUserDAO appUserDAO, TweetDAO tweetDAO) {
        this.appUserDAO = appUserDAO;
        this.tweetDAO = tweetDAO;
    }


    private boolean isUserLoginNoneAvailable(String login) {
        try {
            appUserDAO.getUserByLogin(login);
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    private boolean isUserEmailNoneAvailable(String email) {
        try {
            appUserDAO.getUserByEmail(email);
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    @Override
    public  List<ValidationError> validateUser(AppUser user) {
       List<ValidationError> errors = new ArrayList<>();
        if (isUserEmailNoneAvailable(user.getEmail())){
            errors.add(new ValidationError(EMAIL_ERROR_HEADER, EMAIL_ERROR_MESSAGE));
        }
        if (isUserLoginNoneAvailable(user.getLogin())){
            errors.add(new ValidationError(LOGIN_ERROR_HEADER, LOGIN_IN_USE_ERROR_MESSAGE));
        }
        return errors;
    }

    @Override
    public void register(AppUser user) {
        appUserDAO.saveUser(user);
    }


}
