package oss.gabrielgiussi.hiplay.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class HibernateService {

  private static final Logger log = LoggerFactory.getLogger(HibernateService.class);

  protected SessionFactory sessionFactory;

  protected Class clazz;

  protected String logPrefix;

  @Autowired
  public HibernateService(SessionFactory factory, Class clazz, String logPrefix) {
      this.logPrefix = logPrefix;
      this.clazz = clazz;
      this.sessionFactory = factory;

  }

    @Transactional
    public void loadOneDepartment() {

    }

    @Transactional
    public void loadAllDepartments() {

        log.debug("########## " + logPrefix + " Loading all Departments ##########");

        List<IDepartment> deps = (List<IDepartment>) getSession().createCriteria(clazz).list();

        log.debug("########## " + logPrefix + " Forcing Employees initialization of first SelectDepartment ########## ");
        deps.get(0).getEmployees().get(0).toString();

    }

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
