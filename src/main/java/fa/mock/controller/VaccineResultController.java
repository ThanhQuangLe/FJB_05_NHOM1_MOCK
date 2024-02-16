package fa.mock.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import fa.mock.DTO.VaccineResult.InjectionResultDTO;
import fa.mock.DTO.VaccineResult.InjectionResultListDTO;
import fa.mock.entities.InjectionResult;
import fa.mock.entities.InjectionSchedule;
import fa.mock.entities.Users;
import fa.mock.entities.Vaccine;
import fa.mock.repository.InjectionResultRepository;
import fa.mock.repository.InjectionScheduleRepository;
import fa.mock.repository.UserRepository;
import fa.mock.repository.VaccineRepository;
import fa.mock.service.InjectionResultService;

@Controller
public class VaccineResultController {

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

	@GetMapping(value = {"/vaccineResult-create","/vaccineResult-update/{id}"})
	public String showCreateResultUi(Model model,@PathVariable(value = "id",required = false) Integer id) {
		List<Users> users = userRepository.findAll();
		List<Vaccine> vaccines = vaccineRepository.findAll();
		List<InjectionSchedule>injectionSchedules =	injectionScheduleRepository.findAll();
		
		model.addAttribute("users", users);
		model.addAttribute("vaccines", vaccines);
		model.addAttribute("injectionSchedules", injectionSchedules);
		if (id == null) {
			model.addAttribute("resultDTO", new InjectionResultDTO());
		}else {
			InjectionResult injectionResult =  injectionResultRepository.findById(id).orElse(null);
			InjectionResultDTO dto = new InjectionResultDTO();
			dto.setId(injectionResult.getId());
			dto.setInjectionDate(convertDateToString(injectionResult.getInjectionDate()));
			dto.setInjectionPlace(injectionResult.getInjectionPlace());
			dto.setNextInjectionDate(convertDateToString(injectionResult.getNextInjectionDate()));
			dto.setNumberOfInjection(injectionResult.getNumberOfInjection());
			dto.setPrevention(injectionResult.getPrevention());
			dto.setUserId(injectionResult.getUsers().getId());
			dto.setVaccineId(injectionResult.getVaccine().getId());
			
			model.addAttribute("resultDTO", dto);
		}
		
		
		return "/vaccineResult/ResultCreate";
	}

	@GetMapping("/vaccineResult-list")
	public String showListResultUi(Model model) {
		List<InjectionResultListDTO> injectionResultListDTOs = injectionResultService.listResult();
		model.addAttribute("injectionResultListDTOs", injectionResultListDTOs);
		return "/vaccineResult/ResultList";
	}

	@PostMapping("/vaccineResult-create")
	public String createResult(@ModelAttribute("resultDTO") InjectionResultDTO injectionResultDTO) {
		injectionResultService.saveResult(injectionResultDTO);
		
		return "redirect:/vaccineResult-list";
	}
	
	
	@PostMapping("/vaccineResult-delete")
	@ResponseBody
	public int deleteResult(@RequestBody List<Integer> idList) {
		int result = injectionResultService.deleteResults(idList);
		return result;
	}
	
	public String convertDateToString(Date input) {
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        String dateString = formatter.format(input);
	        return dateString;
	}
	
	@PostMapping("/vaccineResult-search")
	@ResponseBody
	public List<InjectionResultListDTO> searchResult(@RequestBody String input) {
		String searchKey =  input.replace("\"", "");
		System.out.println(input);
		List<InjectionResultListDTO> result = injectionResultService.getResult(searchKey);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
