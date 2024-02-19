package fa.mock.DTO.VaccineResultReport;

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
public class VaccineResultReportDTO {
	private String vaccineName;
	private String prevention;
	private String place;
	private String dateOfInjection;
	private Long numberOfInjection;
}
