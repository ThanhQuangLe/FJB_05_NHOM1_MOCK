package fa.mock.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {

    @Id
    @Column(name = "employee_id",unique = true, length = 36)
    private String id;
    @Column(columnDefinition = "NVARCHAR")
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(length = 100)
    private String email;

    @Column(name = "employee_name", columnDefinition = "NVARCHAR(100)")
    private String employeeName;

    private boolean gender;

    private String image;

    private String password;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String position;

    private String username;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String workingPlace;
}
