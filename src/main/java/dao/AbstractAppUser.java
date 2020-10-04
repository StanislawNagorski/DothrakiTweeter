package dao;

import hibernate.HibernateUtil;

import javax.persistence.EntityManager;

public class AbstractAppUser {

    protected final HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    protected final EntityManager em = hibernateUtil.getEm();

}
