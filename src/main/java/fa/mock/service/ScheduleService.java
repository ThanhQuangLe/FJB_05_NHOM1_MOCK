package fa.mock.service;

import fa.mock.entities.InjectionSchedule;
import fa.mock.repository.InjectionScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    @Autowired
    InjectionScheduleRepository injectionScheduleRepository;

    public InjectionSchedule save(InjectionSchedule injectionSchedule){
        injectionScheduleRepository.save(injectionSchedule);
        return injectionSchedule;
    }
}
