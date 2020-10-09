package services.impl;

import dao.AppUserDAO;
import dao.TweetDAO;
import errors.ValidationError;
import model.AppUser;
import services.AppUserService;

import java.util.ArrayList;
import java.util.List;

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
