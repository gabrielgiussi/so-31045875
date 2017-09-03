package oss.gabrielgiussi.hiplay.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.CollectionStatistics;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import oss.gabrielgiussi.hiplay.subselect.SubselectDepartment;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

public class HibernateService {

  private static final Logger log = LoggerFactory.getLogger(HibernateService.class);

  protected SessionFactory sessionFactory;

  final protected Class depClazz;

  final protected Class empClazz;

  protected String logPrefix;

  final static String EMPLOYEES_ROLE = ".employees";

  @Autowired
  public HibernateService(EntityManagerFactory factory, Class depClazz,Class empClazz, String logPrefix) {
      this.logPrefix = logPrefix;
      this.depClazz = depClazz;
      this.empClazz = empClazz;
      this.sessionFactory = factory.unwrap(SessionFactory.class);
  }

    @Transactional
    public ExampleStatistics loadOneDepartment() {
        clearStats();

        log.debug("########## " + logPrefix + " Loading a single Department with id 1 ##########");

        IDepartment dep = (IDepartment) getSession().get(getDepClazz(),1);

        log.debug("########## " + logPrefix + " Forcing Employees initialization of Department with id 1 ########## ");
        dep.getEmployees().get(0).toString();

        return stats();
    }

    @Transactional
    public ExampleStatistics loadNDepartments() {
        clearStats();

        log.debug("########## " + logPrefix + " Loading all Departments ##########");

        List<IDepartment> deps = (List<IDepartment>) getSession().createCriteria(getDepClazz()).setMaxResults(2).list();

        log.debug("########## " + logPrefix + " Forcing Employees initialization of first Department ########## ");
        for (IDepartment dep : deps) {
            dep.getEmployees().get(0).toString();
        }

        return stats();
    }

    @Transactional
    public ExampleStatistics loadAllDepartments() {
        clearStats();

        log.debug("########## " + logPrefix + " Loading all Departments ##########");

        List<IDepartment> deps = (List<IDepartment>) getSession().createCriteria(getDepClazz()).list();

        log.debug("########## " + logPrefix + " Forcing Employees initialization of first Department ########## ");
        for (IDepartment dep : deps) {
            dep.getEmployees().get(0).toString();
        }

        return stats();
    }

    public Class getDepClazz() {
        return depClazz;
    }

    public Class getEmpClazz() {
        return empClazz;
    }

    private void clearStats(){
        getSessionFactory().getStatistics().clear();
    }

    private ExampleStatistics stats(){
      return new ExampleStatistics(getSessionFactory().getStatistics(),getDepClazz(),getEmpClazz());
    }

  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
