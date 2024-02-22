package fa.mock.service;

import fa.mock.entities.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VaccineTypeService {
    Page<VaccineType> getResult(String input, Pageable pageable);


}
