package fa.mock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import fa.mock.DTO.VaccineResult.InjectionResultListDTO;
import fa.mock.DTO.VaccineResult.PagingDTO;
import fa.mock.DTO.VaccineResultReport.InputReportDTO;
import fa.mock.DTO.VaccineResultReport.VaccineResultReportDTO;
import fa.mock.entities.InjectionResult;
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
		List<Vaccine> vaccines = vaccineRepository.findAll();
		List<VaccineResultReportDTO> injectionResultListDTOs =convertToDTO(injectionResultService.listResultReport(pageable).getContent()) ;
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= contentPage.getTotalPages(); i++) {
			list.add(i);
		}
		model.addAttribute("injectionResultListDTOs", injectionResultListDTOs);
		model.addAttribute("page", contentPage);
		model.addAttribute("pageNumList", list);
		model.addAttribute("vaccines", vaccines);
		return "/vaccineResultReport/ReportResultList";
	}
	
	@PostMapping("/VaccineResult-Report")
	@ResponseBody
	public Map<String, Object> pagingResult(@RequestBody InputReportDTO dto) {
		String injectionDate = dto.getInjectionDate();
		String nextInjectionDate = dto.getNextInjectionDate();
		String vaccineName = dto.getVaccineName();
		String prevention = dto.getPrevention();
		String pageNumData = dto.getPageNumData();
		String yearData = dto.getYear();
		Pageable pageableCheck = PageRequest.of(0, 5);
		
		
		int pageNum = Integer.parseInt(pageNumData);
		int year = Integer.parseInt(yearData);
		
		Page<Object[]> contentPageCheck = null;
		if(injectionDate.isEmpty()&&nextInjectionDate.isEmpty()&&vaccineName.isEmpty()&&prevention.isEmpty()) {
			contentPageCheck = injectionResultService.listResultReport(pageableCheck);
		}else {
			contentPageCheck = injectionResultService.listResultReportSearch(injectionDate,nextInjectionDate,vaccineName,prevention,year,pageableCheck);
		}
		
		
		int checkPage = contentPageCheck.getTotalPages();
		System.out.println(checkPage);
		Pageable pageable=null;
		
		if (checkPage == 0) {
			pageable = PageRequest.of(checkPage, 5);
			pageNum = checkPage;
		} else if (pageNum > checkPage) {
			pageNum = checkPage - 1;
			pageable = PageRequest.of(pageNum, 5);
		} else {
			pageable = PageRequest.of(pageNum - 1, 5);
		}
		Page<Object[]> contentPage = injectionResultService.listResultReportSearch(injectionDate,nextInjectionDate,vaccineName,prevention,year,pageable);
		
		List<VaccineResultReportDTO> injectionResultListDTOs = convertToDTO(contentPage.getContent());
		
		if(!injectionDate.isEmpty()||!nextInjectionDate.isEmpty()||!vaccineName.isEmpty()||!prevention.isEmpty()) {
			
			injectionResultListDTOs =convertToDTO(contentPage.getContent()) ;	
			
		}else if(injectionDate.isEmpty()&&nextInjectionDate.isEmpty()&&vaccineName.isEmpty()&&prevention.isEmpty()||contentPage.getTotalPages()==0) {
			contentPage = injectionResultService.listResultReport(pageable);
			injectionResultListDTOs =convertToDTO(injectionResultService.listResultReport(pageable).getContent()) ;	
		}
		
		
		
		List<Integer> list = new ArrayList<>();
		
		for (int i = 1; i <= contentPage.getTotalPages(); i++) {
			list.add(i);
		}
		
		System.out.println("=================="+injectionResultListDTOs);
		System.out.println("==================totalPage: "+contentPage.getTotalPages());
		
		Map<String, Object> result = new HashMap<String, Object>();
			result.put("list", list);
			result.put("injectionResultListDTOs", injectionResultListDTOs);
			result.put("pageNumber", pageNum - 1);
			result.put("hasPrevious", contentPage.hasPrevious());
			result.put("hasNext", contentPage.hasNext());
			result.put("pageSize", 5);
			result.put("total", contentPage.getTotalElements());
		return result;
	}
	
	
	
}
