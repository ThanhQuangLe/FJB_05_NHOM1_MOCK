package fa.mock.repository;

import fa.mock.entities.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<Users,Integer> {
	@Query(value = "SELECT MAX(id) AS maxId FROM users",nativeQuery = true)
	public String getMaxId();
}
