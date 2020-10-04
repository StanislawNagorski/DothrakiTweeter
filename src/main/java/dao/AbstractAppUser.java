package dao;

import hibarnate.HibarnateUtil;

import javax.persistence.EntityManager;

public class AbstractAppUser {

    protected final HibarnateUtil hibarnateUtil = HibarnateUtil.getInstance();
    protected final EntityManager em = hibarnateUtil.getEm();

}
