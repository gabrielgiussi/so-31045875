package oss.gabrielgiussi.hiplay.app;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import oss.gabrielgiussi.hiplay.select.SelectDepartment;
import oss.gabrielgiussi.hiplay.subselect.SubselectDepartment;

import javax.persistence.EntityManagerFactory;

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
    subselectService().loadAllDepartments();

    selectService().loadAllDepartments();

  }

}
