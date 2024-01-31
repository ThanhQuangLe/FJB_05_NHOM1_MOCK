package fa.mock.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "INJECTION_RESULT")
@Entity
@ToString(exclude = {"customer","vaccine"})
public class InjectionResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "injection_results_id")
	private Integer id;
	
	@Column(name = "injection_date")
	@Temporal(TemporalType.DATE)
	private Date injectionDate;
	
	@Column(name = "injection_place",columnDefinition = "NVARCHAR(255)")
	private String injectionPlace;
	

	@Column(name = "next_injection_date")
	@Temporal(TemporalType.DATE)
	private Date nextInjectionDate;
	
	@Column(name = "number_of_injection")
	private Integer numberOfInjection;
	
	@Column(columnDefinition = "NVARCHAR(100)")
	private String prevention;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "vaccine_id")
	private Vaccine vaccine;
	
}
