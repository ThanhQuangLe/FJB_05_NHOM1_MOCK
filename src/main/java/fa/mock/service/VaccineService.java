package fa.mock.service;

import fa.mock.entities.Vaccine;
import fa.mock.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class VaccineService {

    @Autowired
    VaccineRepository vaccineRepository;


    public Vaccine save(Vaccine vaccine) {
        vaccineRepository.save(vaccine);
        return vaccine;
    }
}
