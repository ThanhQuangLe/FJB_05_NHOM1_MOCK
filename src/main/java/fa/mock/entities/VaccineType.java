package fa.mock.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VACCINE_TYPE")
@Entity
@ToString(exclude = "vaccines")
public class VaccineType {
	
	@Id
	@Column(name = "vaccine_type_id")
	@NotBlank(message = "Must not be empty")
	private String id;
	
	@Column(columnDefinition = "NVARCHAR(200)")
	private String description;
	
	private Boolean status = true;
	
	@Column(name = "vaccine_type_name",columnDefinition = "NVARCHAR(50)")
	@NotBlank(message = "Must not be empty")
	private String vaccineTypeName;
	
	@OneToMany(mappedBy = "vaccineType")
	private List<Vaccine> vaccines;
}
