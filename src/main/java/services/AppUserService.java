package services;

import errors.ValidationError;
import model.AppUser;

import java.util.List;

public interface AppUserService {

    List<ValidationError> validateUser(String login, String email);
    void register(AppUser user);
    boolean isLoginAndPasswordValid(String login, String password);

}
