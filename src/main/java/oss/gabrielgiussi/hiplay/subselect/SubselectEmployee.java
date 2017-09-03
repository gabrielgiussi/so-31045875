package oss.gabrielgiussi.hiplay.subselect;

import javax.persistence.*;

@Entity()
@Table(name = "SUBSELECT_EMPLOYEE")
public class SubselectEmployee {
    @Id
    //@SequenceGenerator(name = "emp_seq", sequenceName = "seq_employee")
    //@GeneratedValue(generator = "emp_seq")
    @Column(name = "EMPLOYEE_ID")
    private Integer employeeId;
    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;


    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private SubselectDepartment department;
}
