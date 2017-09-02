package oss.gabrielgiussi.hiplay.select;

import javax.persistence.*;

@Entity()
@Table(name = "SELECT_EMPLOYEE")
public class SelectEmployee {
    @Id
    @Column(name = "EMPLOYEE_ID")
    private Integer employeeId;
    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private SelectDepartment department;
}
