package fa.mock.repository;

import fa.mock.entities.InjectionResult;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InjectionResultRepository extends JpaRepository<InjectionResult, Integer> {
	
	@Query("SELECT ir FROM InjectionResult ir " +
	           "WHERE ir.users.fullName LIKE %:customerName% OR " +
	           "ir.vaccine.vaccineName LIKE %:vaccineName% OR " +
	           "ir.users.id LIKE %:userid% OR " +
	           "ir.numberOfInjection = :numberOfInjections")
	 Page<InjectionResult>  searchResults(
	        @Param("customerName") String customerName,
	        @Param("vaccineName") String vaccineName,
	        @Param("userid") String userid,
	        @Param("numberOfInjections") int numberOfInjections,Pageable pageable
	    );
	
	 @Query("SELECT ir FROM InjectionResult ir " +
	            "WHERE ir.users.id = :userId " +
	            "AND ir.vaccine.id = :vaccineId " +
	            "AND ir.numberOfInjection = :numberOfInjection " +
	            "AND ir.injectionDate = :checkDate " +
	            "AND ir.nextInjectionDate = :checkNextDate ")
	 List<InjectionResult> checkResult(
	            @Param("userId") String userId,
	            @Param("vaccineId") String vaccineId,
	            @Param("checkDate") Date checkDate,
	            @Param("checkNextDate") Date checkNextDate,
	            @Param("numberOfInjection") Integer numberOfInjection
	    );
	 
	 @Query("SELECT ir.vaccine,ir.injectionDate,ir.injectionPlace,ir.prevention,COUNT(ir.numberOfInjection) AS Num\r\n"
	 		+ "  FROM InjectionResult ir"
	 		+ "  GROUP BY ir.vaccine,ir.injectionDate,ir.injectionPlace,ir.prevention")
	 Page<Object[]> getReport(Pageable pageable);
	 
}
