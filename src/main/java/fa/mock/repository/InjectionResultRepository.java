package fa.mock.repository;

import fa.mock.entities.InjectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjectionResultRepository extends JpaRepository<InjectionResult, Integer> {
}
