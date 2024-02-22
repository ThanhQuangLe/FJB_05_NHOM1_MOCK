package fa.mock.repository;

import fa.mock.entities.RoleEnum;
import fa.mock.entities.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<Users, String> {
	@Query("SELECT MAX(u.id) AS maxId FROM Users u Where u.id like :prefix")
	public String getMaxId(@Param("prefix") String prefix);

	@Query("select u from Users u where u.role = 'ROLE_USER' ")
    List<Users> findAllCustomer();

	@Query("select u from Users u where u.role = 'ROLE_USER' ")
	Page<Users> findAllCustomerPaging(Pageable pageable);

	@Query("select u from Users u where u.role = 'ROLE_USER' and (u.fullName like ?1 or  u.id like ?1)")
	Page<Users> findUsers(String searchTerm, Pageable pageable);

	@Query("select u ,sum(i.numberOfInjection) from Users u join u.injectionResults i" +
			" where u.role = 'ROLE_USER' and u.dateOfBirth >= ?1 and u.dateOfBirth <= ?2 " +
			" and u.fullName like ?3  and u.address like ?4" +
			" GROUP BY u")
	Page<Object[]> findUsersForReport(LocalDate fromDate, LocalDate toDate, String fullName, String address, Pageable pageable);


	@Query("select count(u) from Users u join u.injectionResults i where u.role = 'ROLE_USER' and YEAR(i.injectionDate) = ?1\n" +
			"  AND MONTH(i.injectionDate) = ?2")
	Integer findCustomerForReport(Integer year, Integer month);

	@Query("select u from Users u where u.userName = ?1")
	Users findUsersByUserName(String userName);

	public Users findByUserName(String usersName);

    Page<Users> findById(String s, Pageable pageable);

    @Query("SELECT v.image FROM Vaccine v WHERE v.id = ?1")
    byte[] getImageDataById(String userId);


	@Query("select u from Users u where u.role = 'ROLE_EMPLOYEE' ")
	public  List<Users> findUsersByRole(String role);

	@Query("select u from Users u where u.role = 'ROLE_EMPLOYEE' ")
	public List<Users> findAllEmployee();

	@Query("select u from Users u where u.role = 'ROLE_EMPLOYEE' ")
	Page<Users> findAllEmployeePaging(Pageable pageable1);

	@Query("select u from Users u where u.role = 'ROLE_EMPLOYEE' and (u.fullName like ?1 or  u.id like ?1)")
	Page<Users> findEmployee(String searchTerm, Pageable pageable);
}
