package oss.gabrielgiussi.hiplay.app;

import org.hibernate.stat.Statistics;
import org.springframework.util.Assert;

// https://stackoverflow.com/questions/609351/in-hibernate-statistics-whats-the-difference-between-load-and-fetch
public class ExampleStatistics {

    final static String EMPLOYEES_ROLE = ".employees";

    final Class depClazz;

    final Class empClazz;

    final Statistics stats;

    public ExampleStatistics(Statistics stats, Class depClazz, Class empClazz){
        this.depClazz = depClazz;
        this.empClazz = empClazz;
        this.stats = stats;
    }

    private long fetchedCollectionOfEmployees(){
        return stats.getCollectionStatistics(getEmployeesRole()).getLoadCount(); // Fetch or Load?
    }

    private long fetchedEmployees(){
        return stats.getEntityStatistics(empClazz.getCanonicalName()).getLoadCount();
    }

    private long fetchedDepartments() {
        return stats.getEntityStatistics(depClazz.getCanonicalName()).getLoadCount();
    }

    public void assertFetchedCollectionOfEmployees(long number){
        long fetched = fetchedCollectionOfEmployees();
        Assert.isTrue(fetched == number, "Fetched Collections of Employees: [" + fetched +"], expected [" + number + "]");
    }

    public void assertFetchedEmployees(long number) {
        long fetched = fetchedEmployees();
        Assert.isTrue(fetched == number, "Fetched Employees: [" + fetched +"], expected [" + number + "]");
    }

    public void assertFetchedDepartments(long number) {
        long fetched = fetchedDepartments();
        Assert.isTrue(fetched == number, "Fetched Departments: [" + fetched +"], expected [" + number + "]");
    }

    private String getEmployeesRole(){
        return depClazz.getCanonicalName() + EMPLOYEES_ROLE;
    }
}
