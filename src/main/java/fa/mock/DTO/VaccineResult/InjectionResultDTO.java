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
public class InjectionResultDTO {
	private Integer id;
	
	private String injectionDate;
	
	private String injectionPlace;
	

	private String nextInjectionDate;
	
	private Integer numberOfInjection;
	
	private String prevention;

	private String userId ;
	
	private String vaccineId;
	
}
