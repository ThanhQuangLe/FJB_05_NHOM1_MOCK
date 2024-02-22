package fa.mock.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
	@Pattern(regexp = "[0-9]*", message = "ID must contain only digits")
	@Size(max = 10, message = "ID must be less than or equal 10 digits")
	@NotBlank(message = "ID must not be empty")
	private String id;
	
	@Column(columnDefinition = "NVARCHAR(200)")
	private String contraindication;
	
	@Column(columnDefinition = "NVARCHAR(200)")
	private String indication;
	
	@Column(name = "number_of_injection")
	private Integer numberOfInjection;
	
	@Column(columnDefinition = "NVARCHAR(50)")
	private String origin;
	
	@Column(name = "time_begin_next_injection")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeBeginNextInjection;

	@Column(name = "time_end_next_injection")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeEndNextInjection;
	
	@Column(columnDefinition = "NVARCHAR(200)")
	private String usage;

	private  String image;

	@NotBlank(message = "Vaccine Name must not be empty")
	@Column(name = "vaccine_name",columnDefinition = "NVARCHAR(100)")
	private String vaccineName;
	
	private boolean status = true ;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "vaccine_type_id")
	private VaccineType vaccineType;

	@JsonIgnore // Sử dụng @JsonBackReference ở đây
	@OneToMany(mappedBy = "vaccine")
	private List<InjectionSchedule> injectionSchedules;

	@JsonIgnore // Sử dụng @JsonBackReference ở đây
	@OneToMany(mappedBy = "vaccine")
	private List<InjectionResult> injectionResults;
	
}
