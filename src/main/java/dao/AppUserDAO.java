package dao;

import model.AppUser;

import java.util.HashSet;
import java.util.Optional;

public interface AppUserDAO {

    HashSet<AppUser> getAll();

    Optional<AppUser> getUserById(Long id);

    Optional<AppUser> getUserByEmail(String email);

    Optional<AppUser> getUserByLogin(String login);

    HashSet<AppUser> getFollowedUsers(AppUser loggedUser);

    HashSet<AppUser> getFollowers(AppUser loggedUser);

    HashSet<AppUser> getNotFollowed(AppUser loggedUSer);

    void follow(AppUser loggedUser, AppUser userToFollow);

    void unfollow(AppUser loggedUser, AppUser userToStopFollowing);

    void saveUser(AppUser user);

    void deleteUser(AppUser user);

}
