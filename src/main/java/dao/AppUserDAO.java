package dao;

import model.AppUser;

import java.util.HashSet;

public interface AppUserDAO {

    HashSet<AppUser> getAll();

    AppUser getUserById(Long id);

    AppUser getUserByEmail(String email);

    AppUser getUserByLogin(String login);

    HashSet<AppUser> getFollowedUsers(AppUser loggedUser);

    HashSet<AppUser> getFollowers(AppUser loggedUser);

    HashSet<AppUser> getNotFollowed(AppUser loggedUSer);

    void follow(AppUser loggedUser, AppUser userToFollow);

    void unfollow(AppUser loggedUser, AppUser userToStopFollowing);

    void saveUser(AppUser user);

    void deleteUser(AppUser user);

}
