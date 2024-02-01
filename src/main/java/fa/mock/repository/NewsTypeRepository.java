package fa.mock.repository;

import fa.mock.entities.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTypeRepository extends JpaRepository<NewsType,Integer> {
}
