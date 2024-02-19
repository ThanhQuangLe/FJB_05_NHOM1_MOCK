package fa.mock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fa.mock.DTO.VaccineResultReport.VaccineResultReportDTO;
import fa.mock.entities.Vaccine;
import fa.mock.repository.InjectionResultRepository;
import fa.mock.repository.InjectionScheduleRepository;
import fa.mock.repository.UserRepository;
import fa.mock.repository.VaccineRepository;
import fa.mock.service.InjectionResultService;

@Controller
public class VaccineResultReportController {
	@Autowired
	InjectionResultRepository injectionResultRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VaccineRepository vaccineRepository;

	@Autowired
	InjectionScheduleRepository injectionScheduleRepository;

	@Autowired
	InjectionResultService injectionResultService;
	
	public String convertDateToString(Date input) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = formatter.format(input);
		return dateString;
	}
	
	public List<VaccineResultReportDTO> convertToDTO(List<Object[]> input) {
		List<VaccineResultReportDTO> injectionResultDTOs = new ArrayList<VaccineResultReportDTO>();
		for (Object[] i : input) {
			
			VaccineResultReportDTO dto = new VaccineResultReportDTO();
			dto.setVaccineName(((Vaccine)i[0]).getVaccineName());
			dto.setPrevention((String)i[3]);
			dto.setPlace((String)i[2]);
			dto.setDateOfInjection(convertDateToString((Date)i[1]));
			dto.setNumberOfInjection((Long)i[4]);
			injectionResultDTOs.add(dto);
		}
		return injectionResultDTOs;
	}
	
	@GetMapping("/VaccineResult-Report")
	public String getResultUI(Model model) {
		Pageable pageable = PageRequest.of(0, 5);
		Page<Object[]> contentPage = injectionResultService.listResultReport(pageable);
		List<VaccineResultReportDTO> injectionResultListDTOs =convertToDTO(injectionResultService.listResultReport(pageable).getContent()) ;
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= contentPage.getTotalPages(); i++) {
			list.add(i);
		}
		model.addAttribute("injectionResultListDTOs", injectionResultListDTOs);
		model.addAttribute("page", contentPage);
		model.addAttribute("pageNumList", list);
		return "/vaccineResultReport/ReportResultList";
	}
	
	
	
}
