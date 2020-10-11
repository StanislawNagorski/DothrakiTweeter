package services;

import errors.ValidationError;
import model.AppUser;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface AppUserService {

    List<ValidationError> validateUser(String login, String email);
    void register(AppUser user);
    boolean isLoginAndPasswordValid(String login, String password);
    AppUser getUserByLogin(String login);
    HashSet<AppUser> getFollowers(AppUser loggedUser);
    HashSet<AppUser> getNotFollowed(AppUser loggedUSer);
    HashSet<AppUser> getFollowedUsers(AppUser loggedUser);


}
