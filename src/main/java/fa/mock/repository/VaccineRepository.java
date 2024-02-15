package fa.mock.repository;

import fa.mock.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VaccineRepository extends JpaRepository<Vaccine,String> {
    Page<Vaccine> findByVaccineTypeLike(String vaccineTypeId , Pageable pageable);

    @Query("select v from Vaccine v where v.vaccineType.vaccineTypeName like ?1")
    Page<Vaccine> findByVaccineType (String vaccineTypeName, Pageable pageable);

    @Query("SELECT v.image FROM Vaccine v WHERE v.id = ?1")
    byte[] getImageDataById(String id);

}
