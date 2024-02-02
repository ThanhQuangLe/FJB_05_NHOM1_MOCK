package fa.mock.repository;

import fa.mock.entities.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<Users,String> {
	@Query("SELECT MAX(id) AS maxId FROM Users u Where id like :prefix")
	public String getMaxId(@Param("prefix") String prefix);
}
