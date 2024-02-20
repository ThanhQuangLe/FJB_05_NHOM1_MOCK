package fa.mock.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fa.mock.DTO.VaccineResult.InjectionResultDTO;
import fa.mock.DTO.VaccineResult.InjectionResultListDTO;
import fa.mock.entities.InjectionResult;
import fa.mock.entities.Users;
import fa.mock.repository.InjectionResultRepository;
import fa.mock.repository.UserRepository;
import fa.mock.repository.VaccineRepository;

@Service
public class InjectionResultServiceImpl implements InjectionResultService {

	@Autowired
	InjectionResultRepository injectionResultRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VaccineRepository vaccineRepository;

	@Override
	public InjectionResult saveResult(InjectionResultDTO resultDTO) {
		InjectionResult result = new InjectionResult();

		BeanUtils.copyProperties(resultDTO, result);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			result.setInjectionDate(formatter.parse(resultDTO.getInjectionDate()));
			result.setNextInjectionDate(formatter.parse(resultDTO.getNextInjectionDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setUsers(userRepository.findById(resultDTO.getUserId()).orElse(null));
		result.setVaccine(vaccineRepository.findById(resultDTO.getVaccineId()).orElse(null));
		List<InjectionResult> condition = injectionResultRepository.checkResult(resultDTO.getUserId(),
				resultDTO.getVaccineId(), result.getInjectionDate(), result.getNextInjectionDate(),
				result.getNumberOfInjection());
		if (condition.isEmpty()) {
			return injectionResultRepository.save(result);
		} else {
			return null;
		}

	}

	@Override
	public List<InjectionResultListDTO> listResult(Pageable pageable) {
		List<InjectionResultListDTO> injectionResultDTOs = new ArrayList<InjectionResultListDTO>();
		List<InjectionResult> injectionResults = injectionResultRepository.findAll(pageable).getContent();

		injectionResultDTOs = convertToDTO(injectionResults);
		return injectionResultDTOs;
	}

	@Override
	public int deleteResults(List<Integer> ids) {
		int checkDelete = 0;
		for (Integer id : ids) {
			if (injectionResultRepository.findById(id) != null) {
				checkDelete += 1;
				injectionResultRepository.deleteById(id);
			}
		}
		return checkDelete;
	}

	@Override
	public Page<InjectionResult> getResult(String input, Pageable pageable) {
		int injectNumber = 0;
		try {
			injectNumber = Integer.parseInt(input);
		} catch (Exception e) {
			injectNumber = 0;
			e.printStackTrace();
		}
		Page<InjectionResult> page = injectionResultRepository.searchResults(input, input, input, injectNumber,
				pageable);

		return page;
	}

	@Override
	public Page<InjectionResult> listResultPagging(Pageable pageable) {
		Page<InjectionResult> contentPage = injectionResultRepository.findAll(pageable);
		return contentPage;
	}

//	Đổi từ Date sang string
	public String convertDateToString(Date input) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = formatter.format(input);
		return dateString;
	}

//Đổi từ đối tượng sang dto
	public List<InjectionResultListDTO> convertToDTO(List<InjectionResult> input) {
		List<InjectionResultListDTO> injectionResultDTOs = new ArrayList<InjectionResultListDTO>();
		for (InjectionResult i : input) {
			InjectionResultListDTO injectionResultDTO = new InjectionResultListDTO();
			Users users = i.getUsers();
			injectionResultDTO.setCustomer(users.getId() + "-" + users.getFullName() + "-" + users.getDateOfBirth());
			injectionResultDTO.setVaccineName(i.getVaccine().getVaccineName());
			injectionResultDTO.setPrevention(i.getPrevention());
			injectionResultDTO.setInjectionDate(convertDateToString(i.getInjectionDate()));
			injectionResultDTO.setNextInjectionDate(convertDateToString(i.getNextInjectionDate()));
			injectionResultDTO.setNumberOfInjection(i.getNumberOfInjection());
			injectionResultDTO.setId(i.getId());
			injectionResultDTOs.add(injectionResultDTO);

		}
		return injectionResultDTOs;
	}

	@Override
	public Page<Object[]> listResultReport(Pageable pageable) {
		Page<Object[]> contentPage = injectionResultRepository.getReport(pageable);
		return contentPage;
	}

	@Override
	public Page<Object[]> listResultReportSearch(String injectionDate, String nextInjectionDate, String vaccineName,
			String prevention, int year, Pageable pageable) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date injectionDateData = new Date();
		Date nextInjectionDateData = new Date();
		Calendar calendar = Calendar.getInstance();

		try {
			injectionDateData = formatter.parse(injectionDate);
			nextInjectionDateData = formatter.parse(nextInjectionDate);
		} catch (Exception e) {
			injectionDateData = null;
			nextInjectionDateData = null;
			e.printStackTrace();
		}
		
		if ("".equals(injectionDate)) {
			calendar.set(1, Calendar.JANUARY, 1);
			injectionDateData = calendar.getTime();
		}
		
		if ("".equals(nextInjectionDate)) {
			calendar.set(9999, Calendar.DECEMBER, 31);
			nextInjectionDateData = calendar.getTime();
		}
		
		if ("".equals(vaccineName)) {
			vaccineName = "";
		}
		
		if ("".equals(prevention)) {
			prevention = "";
		}
		Page<Object[]> contentPage = injectionResultRepository.getSearchReport(injectionDateData, nextInjectionDateData,
				vaccineName, prevention, pageable);
		return contentPage;
	}
}
