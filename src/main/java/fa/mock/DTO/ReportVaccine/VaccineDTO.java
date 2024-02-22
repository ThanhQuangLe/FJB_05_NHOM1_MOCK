package fa.mock.DTO.ReportVaccine;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VaccineDTO {
    private Integer pageNumber =1;
    private Integer pageSize =5;

    private String origin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeBeginNextInjection;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeEndNextInjection;

    private String vaccineTypeId;
}
