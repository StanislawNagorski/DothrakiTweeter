package services;

import errors.ValidationError;
import model.AppUser;

import java.util.List;

public interface TweetAppService {

    List<ValidationError> validateUser(AppUser user);
    void register(AppUser user);

}
