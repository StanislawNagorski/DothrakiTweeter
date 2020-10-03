package hibarnate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibarnateUtil {

    private static HibarnateUtil instance;
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("myDatabase");
    private final EntityManager em = factory.createEntityManager();

    private HibarnateUtil(){ }

    public static HibarnateUtil getInstance() {
        if (instance == null){
            instance = new HibarnateUtil();
        }
        return instance;
    }

    public void save(Object o){
        em.getTransaction().begin();
        if (em.contains(o)){
            em.persist(o);
            em.flush();
        }
        em.getTransaction().commit();
    }

    public void delete(Class c, Long id){
        em.getTransaction().begin();
        Object o = em.find(c, id);
        if (null != o){
            em.remove(o);
        }
        em.getTransaction().commit();
    }

    

}

