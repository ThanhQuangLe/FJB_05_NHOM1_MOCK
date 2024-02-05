package fa.mock.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fa.mock.DTO.VaccineResult.InjectionResultDTO;
import fa.mock.DTO.VaccineResult.InjectionResultListDTO;
import fa.mock.entities.InjectionResult;

public interface InjectionResultService {
	
	InjectionResult saveResult(InjectionResultDTO result);
	
	List<InjectionResultListDTO>  listResult(Pageable pageable);
	
	int deleteResults(List<Integer> ids);

	List<InjectionResultListDTO> getResult(String input);

	 Page<InjectionResult> listResultPagging(Pageable pageable);	
}
