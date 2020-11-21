package services;

import errors.ValidationError;
import model.AppUser;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface AppUserService {

    Optional<ValidationError> validateLogin(String login);
    Optional<ValidationError> validateEmail(String email);
    List<ValidationError> validateUser(String login, String email);
    List<ValidationError> loginValidation(String login, String password);
    void register(AppUser user);
    void deactivate(AppUser user);
    AppUser getUserByLogin(String login);
    HashSet<AppUser> getFollowers(AppUser loggedUser);
    HashSet<AppUser> getFollowers(AppUser loggedUser, int offset, int limit);
    int numberOfFollowers(AppUser loggedUser);
    HashSet<AppUser> getNotFollowed(AppUser loggedUSer);
    HashSet<AppUser> getFollowedUsers(AppUser loggedUser);
    void followUser(AppUser loggedUser, AppUser userToFollow);
    void unfollowUser(AppUser loggedUser, AppUser userToFollow);

    void changeLogin(AppUser appUser, String newLogin);
    void changeName(AppUser appUser, String newName);
    void changeLastName(AppUser appUser, String newLastName);
    void changeEmail(AppUser appUser, String newEmail);
    void changePassword(AppUser appUser, String newPassword);
    void changeAvatar(AppUser appUser, String avatarPath);






}
