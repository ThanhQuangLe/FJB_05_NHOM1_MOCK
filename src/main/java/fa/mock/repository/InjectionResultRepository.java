package fa.mock.repository;

import fa.mock.entities.InjectionResult;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InjectionResultRepository extends JpaRepository<InjectionResult, Integer> {
	
	@Query("SELECT ir FROM InjectionResult ir " +
	           "WHERE ir.users.fullName LIKE %:customerName% OR " +
	           "ir.vaccine.vaccineName LIKE %:vaccineName% OR " +
	           "ir.numberOfInjection = :numberOfInjections")
	    List<InjectionResult> searchResults(
	        @Param("customerName") String customerName,
	        @Param("vaccineName") String vaccineName,
	        @Param("numberOfInjections") int numberOfInjections
	    );
}
