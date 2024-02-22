package fa.mock.repository;

import fa.mock.entities.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VaccineTypeRepository extends JpaRepository<VaccineType,String> {
    @Query("SELECT a FROM VaccineType a WHERE " +
            "a.id LIKE %:id% OR " +
            "a.vaccineTypeName LIKE %:vaccineTypeName% OR " +
            "a.description LIKE %:description%")
    Page<VaccineType> searchResults(
            @Param("id") String id,
            @Param("vaccineTypeName") String vaccineTypeName,
            @Param("description") String description,
            Pageable pageable
    );


}
