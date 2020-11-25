package dao;

import model.AppUser;

import java.util.HashSet;
import java.util.Optional;

public interface AppUserDAO {

    HashSet<AppUser> getAll();
    Optional<AppUser> getUserById(Long id);
    Optional<AppUser> getUserByEmail(String email);
    Optional<AppUser> getUserByExternalLogin(String externalLogin);
    Optional<AppUser> getUserByLogin(String login);
    HashSet<AppUser> getFollowedUsers(AppUser loggedUser);
    HashSet<AppUser> getFollowedUsers(AppUser loggedUser, int offset, int limit);
    HashSet<AppUser> getFollowers(AppUser loggedUser);
    HashSet<AppUser> getFollowers(AppUser loggedUser,  int offset, int limit);
    HashSet<AppUser> getNotFollowed(AppUser loggedUSer);

    void follow(AppUser loggedUser, AppUser userToFollow);

    void unfollow(AppUser loggedUser, AppUser userToStopFollowing);

    void saveUser(AppUser user);

    void deleteUser(AppUser user);
    void setLogin(AppUser appUser, String newLogin);
    void setName(AppUser appUser, String newName);
    void setLastName(AppUser appUser, String newLastName);
    void setEmail(AppUser appUser, String newEmail);
    void setPassword(AppUser appUser, String newPassword);

    void setAvatar(AppUser appUser, String avatarPath);
}
