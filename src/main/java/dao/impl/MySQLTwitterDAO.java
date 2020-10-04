package dao.impl;

import dao.AbstractMySQLDAO;
import dao.TweetDAO;
import model.AppUser;
import model.Tweet;

import javax.persistence.TypedQuery;
import java.util.List;

public class MySQLTwitterDAO extends AbstractMySQLDAO implements TweetDAO {

    @Override
    public void saveTweet(Tweet tweet) {
        hibernateUtil.save(tweet);
    }

    @Override
    public void deleteTweet(Long id) {
        hibernateUtil.delete(Tweet.class, id);
    }

    @Override
    public List<Tweet> getUserTweets(AppUser user) {
        TypedQuery<Tweet> query = em.createQuery("from Tweet t where t.author=:login", Tweet.class);
        query.setParameter("login", user.getLogin());

        return query.getResultList();
    }

    @Override
    public Tweet getTweet(Long id) {
//        TypedQuery<Tweet> query = em.createQuery("from Tweet t where t.id=:id", Tweet.class);
//        query.setParameter("id", id);
//        return query.getSingleResult();
        return em.find(Tweet.class, id);
    }
}
