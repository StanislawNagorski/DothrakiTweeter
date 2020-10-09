package services;

import errors.ValidationError;
import model.AppUser;

import java.util.List;

public interface AppUserService {

    List<ValidationError> validateUser(AppUser user);
    void register(AppUser user);

}
