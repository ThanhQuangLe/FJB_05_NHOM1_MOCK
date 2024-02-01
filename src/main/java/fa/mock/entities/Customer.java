package fa.mock.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMER")
@Entity
@ToString(exclude = "injectionResults")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "NVARCHAR(255)")
	private String address;
	
	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(columnDefinition = "NVARCHAR(100)")
	private String email;
	
	@Column(name = "full_name",columnDefinition = "NVARCHAR(100)")
	private String fullName;
	
	
	private Boolean gender;
	
	@Column(name = "identity_card",columnDefinition = "NVARCHAR(24)")
	private String identityCard;
	
	@Column(columnDefinition = "NVARCHAR(255)")
	private String passWord;
	
	private String phone;
	
	@Column(name = "user_name", columnDefinition = "NVARCHAR(255)")
	private String userName;
	
	@OneToMany(mappedBy = "customer")
	private List<InjectionResult> injectionResults;
	
}