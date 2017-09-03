package oss.gabrielgiussi.hiplay.app;

import org.hibernate.stat.CollectionStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import oss.gabrielgiussi.hiplay.batch.BatchDepartment;
import oss.gabrielgiussi.hiplay.batch.BatchEmployee;
import oss.gabrielgiussi.hiplay.select.SelectDepartment;
import oss.gabrielgiussi.hiplay.select.SelectEmployee;
import oss.gabrielgiussi.hiplay.subselect.SubselectDepartment;
import oss.gabrielgiussi.hiplay.subselect.SubselectEmployee;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@EntityScan(basePackages = {"oss.gabrielgiussi.hiplay.select", "oss.gabrielgiussi.hiplay.subselect", "oss.gabrielgiussi.hiplay.batch"})
public class App implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateService.class);

    final static int DEP1_EMPLOYEES = 9;

    final static int DEP2_EMPLOYEES = 4;

    final static int DEP3_EMPLOYEES = 3;

    @Autowired
    EntityManagerFactory factory;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.exit(0);
    }

    @Bean
    public HibernateService subselectService() {
        return new HibernateService(factory, SubselectDepartment.class, SubselectEmployee.class, "[FetchMode.SUBSELECT]");
    }

    @Bean
    public HibernateService selectService() {
        return new HibernateService(factory, SelectDepartment.class, SelectEmployee.class, "[FetchMode.SELECT]");
    }


    @Bean
    public HibernateService batchService(){
        return new HibernateService(factory, BatchDepartment.class, BatchEmployee.class, "[FetchMode.BATCH]");
    }

  // @Bean
  // public HibernateJpaSessionFactoryBean sessionFactory() {
  // return new HibernateJpaSessionFactoryBean();
  // }

    public void run(String... args) throws Exception {

        try {
            scenario1(); // SingleDepartment
            scenario2(); // SubsetOfDepartments
            scenario3(); // AllDepartments
            log.debug("########## All Assertions Correct ##########");
        } catch (java.lang.IllegalArgumentException e){
            log.error(e.getMessage());
        }
    }

    public void scenario1(){

        log.debug("########## Scenario 1: Testing FetchModes querying a single Department  ##########");

        log.debug("########## Scenario 1.1 SingleDepartment:SELECT ##########");
        ExampleStatistics stats11 = selectService().loadOneDepartment();
        stats11.assertFetchedDepartments(1);
        stats11.assertFetchedCollectionOfEmployees(1);
        stats11.assertFetchedEmployees(DEP1_EMPLOYEES);

        log.debug("########## Scenario 1.2 SingleDepartment:SUBSELECT ##########");
        ExampleStatistics stats12 = subselectService().loadOneDepartment();
        stats12.assertFetchedDepartments(1);
        stats12.assertFetchedCollectionOfEmployees(1);
        stats12.assertFetchedEmployees(DEP1_EMPLOYEES);

        log.debug("########## Scenario 1.3 SingleDepartment:BATCH ##########");
        ExampleStatistics stats13 = batchService().loadOneDepartment();
        stats13.assertFetchedDepartments(1);
        stats13.assertFetchedCollectionOfEmployees(1);
        stats13.assertFetchedEmployees(DEP1_EMPLOYEES);



    }

    public void scenario2(){

        log.debug("########## Scenario 2: Testing FetchModes querying 2 Departments (of 3)  ##########");

        log.debug("########## Scenario 2.1 SubsetOfDepartments:SELECT ##########");
        ExampleStatistics stats21 = selectService().loadNDepartments();
        stats21.assertFetchedDepartments(2);
        stats21.assertFetchedCollectionOfEmployees(2);
        stats21.assertFetchedEmployees(DEP1_EMPLOYEES + DEP2_EMPLOYEES);

        log.debug("########## Scenario 2.2 SubsetOfDepartments:SUBSELECT  ##########");
        ExampleStatistics stats22 = subselectService().loadNDepartments();
        // The Department with id = 3 is fetched when the SubselectEmployee#300 is hydrated
        // Resolving associations for [oss.gabrielgiussi.hiplay.subselect.SubselectEmployee#300]
        stats22.assertFetchedDepartments(3);
        stats22.assertFetchedCollectionOfEmployees(3);
        stats22.assertFetchedEmployees(DEP1_EMPLOYEES + DEP2_EMPLOYEES + DEP3_EMPLOYEES);

        log.debug("########## Scenario 2.3 SubsetOfDepartments:BATCH ##########");
        ExampleStatistics stats23 = batchService().loadNDepartments();
        stats23.assertFetchedDepartments(2);
        stats23.assertFetchedCollectionOfEmployees(2);
        stats23.assertFetchedEmployees(DEP1_EMPLOYEES + DEP2_EMPLOYEES);
    }

    public void scenario3(){

        log.debug("########## Scenario 3: Testing FetchModes querying all Departments  ##########");

        log.debug("########## Scenario 3.1 AllDepartments:SELECT  ##########");
        ExampleStatistics stats31 = selectService().loadAllDepartments();
        stats31.assertFetchedDepartments(3);
        stats31.assertFetchedCollectionOfEmployees(3);
        stats31.assertFetchedEmployees(DEP1_EMPLOYEES + DEP2_EMPLOYEES + DEP3_EMPLOYEES);

        log.debug("########## Scenario 3.2 AllDepartments:SUBSELECT  ##########");
        ExampleStatistics stats32 = subselectService().loadAllDepartments();
        stats32.assertFetchedDepartments(3);
        stats32.assertFetchedCollectionOfEmployees(3);
        stats32.assertFetchedEmployees(DEP1_EMPLOYEES + DEP2_EMPLOYEES + DEP3_EMPLOYEES);

        log.debug("########## Scenario 3.3 AllDepartments:BATCH ##########");
        ExampleStatistics stats33 = batchService().loadAllDepartments();
        stats33.assertFetchedDepartments(3);
        stats33.assertFetchedCollectionOfEmployees(3);
        stats33.assertFetchedEmployees(DEP1_EMPLOYEES + DEP2_EMPLOYEES + DEP3_EMPLOYEES);

    }

}
