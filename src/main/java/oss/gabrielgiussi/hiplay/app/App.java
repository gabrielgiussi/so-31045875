package oss.gabrielgiussi.hiplay.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "oss.gabrielgiussi.hiplay.entities")
public class App implements CommandLineRunner {

  @Autowired
  HibernateService service;

  public static void main(String[] args) {
    SpringApplication.run(App.class);
    System.exit(0);
  }

  // @Bean
  // public HibernateJpaSessionFactoryBean sessionFactory() {
  // return new HibernateJpaSessionFactoryBean();
  // }

  public void run(String... args) throws Exception {
    service.service();
  }

}
