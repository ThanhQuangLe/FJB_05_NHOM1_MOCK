package fa.mock.repository;

import fa.mock.entities.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineTypeRepository extends JpaRepository<VaccineType,String> {
}
