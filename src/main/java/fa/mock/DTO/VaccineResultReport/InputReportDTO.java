package fa.mock.DTO.VaccineResultReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InputReportDTO {
	
	private String injectionDate;
	private String nextInjectionDate;
	private String vaccineName;
	private String prevention;
	private String pageNumData;
	private String year;
	
}
