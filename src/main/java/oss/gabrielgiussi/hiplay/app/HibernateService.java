package oss.gabrielgiussi.hiplay.app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import oss.gabrielgiussi.hiplay.entities.EntityA;
import oss.gabrielgiussi.hiplay.entities.EntityB;

@Component
public class HibernateService {

  private static final Logger log = LoggerFactory.getLogger(HibernateService.class);

  protected SessionFactory sessionFactory;

  @Autowired
  public HibernateService(EntityManagerFactory factory) {
    this.sessionFactory = factory.unwrap(SessionFactory.class);
  }

  @Transactional
  public void service() {
    log.debug("########## Starting test 1 ##########");

    log.debug("########## Retrieving A1 ##########");
    EntityA a1 = (EntityA) getSession().get(EntityA.class, Long.valueOf(1));

    log.debug("########## Retrieving B1 ##########");
    EntityB b1 = (EntityB) getSession().get(EntityB.class, Long.valueOf(1));

    log.debug("########## Retrieving A1's set of B ##########");
    List<EntityB> setOfB = new ArrayList<EntityB>(a1.getSetOfB());
    java.util.Collections.sort(setOfB, new Comparator<EntityB>() {

      public int compare(EntityB o1, EntityB o2) {
        return o1.getId().compareTo(o2.getId());
      }
    });
    EntityB b1byCollection = setOfB.get(0);

    if (b1 == b1byCollection) {
      log.debug("########## The object hasn't been loaded twice ##########");
    }

  }

  @Transactional
  public EntityB service2() {
    log.debug("########## Starting test 2 ##########");

    log.debug("########## Retrieving A1 ##########");
    EntityA a1 = (EntityA) getSession().get(EntityA.class, Long.valueOf(1));

    log.debug("########## Force A1 setOfB initialization ##########");
    a1.getSetOfB().size();

    return a1.getSetOfB().iterator().next();
  }

  @Transactional
  public Object[] service3() {
    log.debug("########## Starting test 3 ##########");

    log.debug("########## Retrieving A1 ##########");
    EntityA a1 = (EntityA) getSession().get(EntityA.class, Long.valueOf(1));

    log.debug("########## Force A1 setOfB initialization ##########");
    a1.getSetOfB().size();

    EntityB b1 = a1.getSetOfB().iterator().next();

    b1.getSetOfA().size();

    return new Object[] {
        b1, a1
    };
  }

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
