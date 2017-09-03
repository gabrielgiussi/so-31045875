package oss.gabrielgiussi.hiplay.batch;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import oss.gabrielgiussi.hiplay.app.IDepartment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "BATCH_DEPARTMENT")
public class BatchDepartment implements IDepartment {
    @Id
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @Column(name = "LOCATION")
    private String location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", orphanRemoval = true)
    @Fetch(FetchMode.SELECT) // I guess this is not necessary if we specify @BatchSize
    @BatchSize(size = 2)
    private List<BatchEmployee> employees = new ArrayList<>();

    public String getDepartmentName() {
        return departmentName;
    }

    public List<BatchEmployee> getEmployees() {
        return employees;
    }
}
