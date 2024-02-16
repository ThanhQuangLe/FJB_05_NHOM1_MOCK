package fa.mock.DTO.Customer;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class CustomerDTO {

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirthFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirthTo;

    private String fullName;

    Integer pageNumber = 1;

    Integer pageSize = 5;

}
