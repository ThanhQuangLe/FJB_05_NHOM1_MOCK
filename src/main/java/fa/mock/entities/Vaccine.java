package fa.mock.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "VACCINE")
@Entity
@ToString(exclude = {"injectionSchedules","vaccineType","injectionResults"})
public class Vaccine {
	
	@Id
	@Column(name = "vaccine_id", columnDefinition = "NVARCHAR(36)")
	private String id;
	
	@Column(columnDefinition = "NVARCHAR(200)")
	private String contraindication;
	
	@Column(columnDefinition = "NVARCHAR(200)")
	private String indication;
	
	@Column(name = "number_of_injection")
	private Integer numberOfInjection;
	
	@Column(columnDefinition = "NVARCHAR(50)")
	private String origin;
	
	@Column(name = "time_begin_next_injecttion")
	@Temporal(TemporalType.DATE)
	private Date timeBeginNextInjecttion;
	
	@Column(name = "time_end_next_injecttion")
	@Temporal(TemporalType.DATE)
	private Date timeEndNextInjection;
	
	@Column(columnDefinition = "NVARCHAR(200)")
	private String usage;
	
	@Column(name = "vaccine_name",columnDefinition = "NVARCHAR(100)")
	private String vaccineName;
	
	@ManyToOne
	@JoinColumn(name = "vaccine_type_id")
	private VaccineType vaccineType;
	
	@OneToMany(mappedBy = "vaccine")
	private List<InjectionSchedule> injectionSchedules;
	
	@OneToMany(mappedBy = "vaccine")
	private List<InjectionResult> injectionResults;
	
}
