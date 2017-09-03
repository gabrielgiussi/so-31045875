package oss.gabrielgiussi.hiplay.batch;

import javax.persistence.*;

@Entity()
@Table(name = "BATCH_EMPLOYEE")
public class BatchEmployee {
    @Id
    @Column(name = "EMPLOYEE_ID")
    private Integer employeeId;
    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private BatchDepartment department;
}
