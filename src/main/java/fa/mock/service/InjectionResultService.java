package fa.mock.service;

import java.util.List;

import fa.mock.DTO.VaccineResult.InjectionResultDTO;
import fa.mock.DTO.VaccineResult.InjectionResultListDTO;
import fa.mock.entities.InjectionResult;

public interface InjectionResultService {
	
	InjectionResult saveResult(InjectionResultDTO result);
	
	List<InjectionResultListDTO>  listResult();
	
	int deleteResults(List<Integer> ids);

	List<InjectionResultListDTO> getResult(String input);	
}
