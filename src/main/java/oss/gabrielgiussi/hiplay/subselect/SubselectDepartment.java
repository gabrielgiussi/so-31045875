package oss.gabrielgiussi.hiplay.subselect;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import oss.gabrielgiussi.hiplay.app.IDepartment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "SUBSELECT_DEPARTMENT")
public class SubselectDepartment implements IDepartment {
    @Id
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @Column(name = "LOCATION")
    private String location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<SubselectEmployee> employees = new ArrayList<>();

    public String getDepartmentName() {
        return departmentName;
    }

    public List<SubselectEmployee> getEmployees() {
        return employees;
    }
}
