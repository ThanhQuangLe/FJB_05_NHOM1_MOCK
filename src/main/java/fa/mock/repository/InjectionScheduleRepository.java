package fa.mock.repository;

import fa.mock.entities.InjectionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjectionScheduleRepository extends JpaRepository<InjectionSchedule,Integer> {
}
