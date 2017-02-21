package oss.gabrielgiussi.hiplay.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import oss.gabrielgiussi.hiplay.entities.EntityA;
import oss.gabrielgiussi.hiplay.entities.EntityB;

@SpringBootApplication
@EntityScan(basePackages = "oss.gabrielgiussi.hiplay.entities")
public class App implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(HibernateService.class);

  @Autowired
  HibernateService service;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
    System.exit(0);
  }

  // @Bean
  // public HibernateJpaSessionFactoryBean sessionFactory() {
  // return new HibernateJpaSessionFactoryBean();
  // }

  public void run(String... args) throws Exception {
    int test = Integer.parseInt(args[0]);
    switch (test) {
    case 1:
      service.service();
      break;
    case 2:
      EntityB detachedB = service.service2();
      try {
        detachedB.getSetOfA().size();
      } catch (org.hibernate.LazyInitializationException e) {
        log.debug("########## You are trying to initialize a outside a transaction. ##########");
      }
    case 3:
      Object[] aAndB = service.service3();
      EntityB b1 = (EntityB) aAndB[0];
      EntityA a1 = (EntityA) aAndB[1];
      if (b1.getSetOfA().iterator().next() == a1) {
        log.debug("########## B1.setOfA.get(0) points to the instance A1 loaded by session.get('A',1). ##########");
      }

    default:
      break;
    }

  }

}
