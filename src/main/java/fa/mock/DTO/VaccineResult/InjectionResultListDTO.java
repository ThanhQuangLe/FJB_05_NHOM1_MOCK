package fa.mock.DTO.VaccineResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InjectionResultListDTO {
	private Integer id;
	private String customer;
	private String vaccineName;
	private String prevention;
	private Integer numberOfInjection;
	private String injectionDate;
	private String nextInjectionDate;
}
