package dao.impl;

import dao.AbstractAppUser;
import dao.AppUserDAO;
import model.AppUser;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;

public class MySQLUserDAO extends AbstractAppUser implements AppUserDAO {


    @Override
    public HashSet<AppUser> getAll() {
        TypedQuery<AppUser> getAll = em.createQuery("from AppUser u", AppUser.class);
        List<AppUser> resultList = getAll.getResultList();

        return new HashSet<>(resultList);
    }

    @Override
    public AppUser getUserById(Long id) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.id=:id", AppUser.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public AppUser getUserByEmail(String email) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.email=:email", AppUser.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public AppUser getUserByLogin(String login) {
        TypedQuery<AppUser> query = em.createQuery("select u from AppUser u where u.login=:login", AppUser.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }

    @Override
    public HashSet<AppUser> getFollowedUsers(AppUser loggedUser) {
        return new HashSet(loggedUser.getFollowing());
    }

    @Override
    public HashSet<AppUser> getNotFollowed(AppUser loggedUSer) {
        Query query = em.createQuery("select u from AppUser u where u not in :following ");
        query.setParameter("following", new HashSet(loggedUSer.getFollowing()));

        return new HashSet(query.getResultList());
    }

    @Override
    public HashSet<AppUser> getFollowers(AppUser loggedUser) {
        Query query = em.createQuery("select followers from AppUser u where u.id = :userID");
        query.setParameter("userID", loggedUser.getLogin());

        return new HashSet<>(query.getResultList());
    }


    @Override
    public void follow(AppUser loggedUser, AppUser userToFollow) {
        loggedUser.getFollowing().add(userToFollow);
        saveUser(loggedUser);
    }

    @Override
    public void unfollow(AppUser loggedUser, AppUser userToStopFollowing) {
        loggedUser.getFollowing().remove(userToStopFollowing);
        saveUser(loggedUser);
    }

    @Override
    public void saveUser(AppUser user) {
        hibarnateUtil.save(user);
    }

    @Override
    public void removeUser(Long id) {
        hibarnateUtil.delete(AppUser.class,id);
    }
}
