package fa.mock.repository;

import fa.mock.entities.InjectionSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjectionScheduleRepository extends JpaRepository<InjectionSchedule,Integer> {
    Page<InjectionSchedule> findById(String s, Pageable pageable);
}
