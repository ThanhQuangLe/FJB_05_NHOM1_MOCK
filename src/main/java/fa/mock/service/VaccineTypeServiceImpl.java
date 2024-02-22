package fa.mock.service;

import fa.mock.entities.VaccineType;
import fa.mock.repository.VaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VaccineTypeServiceImpl implements VaccineTypeService {

    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    @Override
    public Page<VaccineType> getResult(String input, Pageable pageable) {
    Page<VaccineType> page = vaccineTypeRepository.searchResults(input, input, input, pageable);
        return page;
    }

}
