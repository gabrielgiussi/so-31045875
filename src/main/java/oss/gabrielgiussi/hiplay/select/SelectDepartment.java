package oss.gabrielgiussi.hiplay.select;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import oss.gabrielgiussi.hiplay.app.IDepartment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "SELECT_DEPARTMENT")
public class SelectDepartment implements IDepartment {
    @Id
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @Column(name = "LOCATION")
    private String location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<SelectEmployee> employees = new ArrayList<>();

    public String getDepartmentName() {
        return departmentName;
    }

    public List<SelectEmployee> getEmployees() {
        return employees;
    }
}
