package oss.gabrielgiussi.hiplay.app;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import oss.gabrielgiussi.hiplay.select.SelectDepartment;
import oss.gabrielgiussi.hiplay.subselect.SubselectDepartment;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackageClasses = PersistenceConfig.class)
public class App implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(HibernateService.class);

  @Autowired
  SessionFactory factory;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
    System.exit(0);
  }

  @Bean
  public HibernateService subselectService(){
    return new HibernateService(factory, SubselectDepartment.class,"[FetchMode.SUBSELECT]");
  }

  @Bean
  public HibernateService selectService(){
    return new HibernateService(factory, SelectDepartment.class,"[FetchMode.SELECT]");
  }

  // @Bean
  // public HibernateJpaSessionFactoryBean sessionFactory() {
  // return new HibernateJpaSessionFactoryBean();
  // }

  public void run(String... args) throws Exception {
    scenario1();
    scenario2();
    scenario3();

  }

  public void scenario1(){

    log.debug("########## Scenario 1: Testing FetchModes querying a single Department  ##########");

    log.debug("########## Scenario 1.1 SingleDepartment:SELECT ##########");
    selectService().loadOneDepartment();

    log.debug("########## Scenario 1.2 SingleDepartment:SUBSELECT ##########");
    subselectService().loadOneDepartment();

  }

  public void scenario2(){

    log.debug("########## Scenario 2: Testing FetchModes querying 2 Departments (of 3)  ##########");

    log.debug("########## Scenario 2.1 SubsetOfDepartments:SELECT ##########");
    selectService().loadNDepartments();

    log.debug("########## Scenario 2.2 SubsetOfDepartments:SUBSELECT  ##########");
    subselectService().loadNDepartments();

  }

  public void scenario3(){

    log.debug("########## Scenario 3: Testing FetchModes querying all Departments  ##########");

    log.debug("########## Scenario 3.1 AllDepartments:SELECT  ##########");
    selectService().loadAllDepartments();

    log.debug("########## Scenario 3.2 AllDepartments:SUBSELECT  ##########");
    subselectService().loadAllDepartments();

  }

}
