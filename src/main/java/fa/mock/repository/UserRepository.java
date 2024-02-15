package fa.mock.repository;

import fa.mock.entities.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,String> {
	@Query("SELECT MAX(u.id) AS maxId FROM Users u Where u.id like :prefix")
	public String getMaxId(@Param("prefix") String prefix);

	@Query("select u from Users u where u.role = 'ROLE_USER' ")
    List<Users> findAllCustomer();

	@Query("select u from Users u where u.role = 'ROLE_USER' ")
	Page<Users> findAllCustomerPaging(Pageable pageable);

	@Query("select u from Users u where u.role = 'ROLE_USER' and (u.fullName like ?1 or  u.id like ?1)")
	Page<Users> findUsers(String searchTerm, Pageable pageable);
}
