package fa.mock.repository;

import fa.mock.entities.Vaccine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


public interface VaccineRepository extends JpaRepository<Vaccine,String> {
    Page<Vaccine> findByVaccineTypeLike(String vaccineTypeId , Pageable pageable);

    @Query("select v from Vaccine v where v.vaccineType.vaccineTypeName like ?1")
    Page<Vaccine> findByVaccineType (String vaccineTypeName, Pageable pageable);

    @Query("SELECT v.image FROM Vaccine v WHERE v.id = ?1")
    byte[] getImageDataById(String id);

    @Transactional
    @Modifying
    @Query("UPDATE Vaccine v SET v.status = false WHERE v.vaccineType.id = ?1")
    Integer updateVaccinesStatusFalse (String vaccineTypeId);

    @Transactional
    @Modifying
    @Query("UPDATE Vaccine v SET v.status = true WHERE v.vaccineType.id = ?1")
    Integer updateVaccinesStatusTrue (String vaccineTypeId);


    @Query("SELECT u, sum(v.numberOfInjection) FROM Vaccine u JOIN u.vaccineType i JOIN u.injectionResults v" +
            " WHERE u.origin LIKE ?1 AND u.vaccineType.id like ?2 " +
            "AND u.timeBeginNextInjection >= ?3 AND u.timeEndNextInjection <= ?4 GROUP BY u")
    Page<Object[]> findVaccineForReport(String origin, String vaccineType, LocalDate begin, LocalDate end, Pageable pageable);

//    @Query("SELECT u, sum(v.numberOfInjection) FROM Vaccine u JOIN u.vaccineType i JOIN u.injectionResults v" +
//            " WHERE u.origin LIKE ?1" +
//            "AND u.timeBeginNextInjection >= ?2 AND u.timeEndNextInjection <= ?3 GROUP BY u")
//    Page<Object[]> findVaccineFor3Report(String origin, LocalDate begin, LocalDate end, Pageable pageable);

    @Query("SELECT count(v) FROM Vaccine v JOIN v.injectionSchedules i WHERE YEAR(i.startDate) = ?1 AND MONTH(i.startDate) =?2")
    Integer findVaccineName(Integer year, Integer mount);


}
