package fa.mock.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "injectionResults")
public class Users {

	@Id
	@Column(name = "user_id")
	private String id;

	@Column(columnDefinition = "NVARCHAR(255)")
	private String address;

	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Column(columnDefinition = "NVARCHAR(100)")
	private String email;

	@Column(name = "full_name", columnDefinition = "NVARCHAR(100)")
	private String fullName;

	private Boolean gender;

	private String image;

	@Column(name = "identity_card", columnDefinition = "NVARCHAR(24)")
	private String identityCard;

	private String password;

	private String phone;

	@Column(columnDefinition = "NVARCHAR(100)")
	private String position;

	@Column(name = "user_name", columnDefinition = "NVARCHAR(255)")
	private String userName;

	@Column(columnDefinition = "NVARCHAR(255)")
	private String workingPlace;

	@JsonIgnore
	@OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
	private List<InjectionResult> injectionResults;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private RoleEnum role;

}
