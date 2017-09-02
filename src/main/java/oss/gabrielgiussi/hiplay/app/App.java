package oss.gabrielgiussi.hiplay.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import oss.gabrielgiussi.hiplay.select.SelectDepartment;
import oss.gabrielgiussi.hiplay.subselect.SubselectDepartment;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@EntityScan(basePackages = {"oss.gabrielgiussi.hiplay.select", "oss.gabrielgiussi.hiplay.subselect"})
public class App implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(HibernateService.class);

  @Autowired
  EntityManagerFactory factory;

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
