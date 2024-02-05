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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fa.mock.DTO.VaccineResult.Const;
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
import fa.mock.service.InjectionResultServiceImpl;

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
	public String showCreateResultUi(Model model,
			@PathVariable(value = "id",required = false) Integer id) {
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
	public String showListResultUi(Model model){
		Pageable pageable = PageRequest.of(0 , 5);
		List<InjectionResultListDTO> injectionResultListDTOs = injectionResultService.listResult(pageable);
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
	
	@PostMapping("/vaccineResult-paging")
	@ResponseBody
	public Map<String, Object>pagingResult(
			@RequestParam(value = "pageNum", defaultValue = Const.PAGE_NUM_DEFAULT) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Const.PAGE_SIZE_DEFAULT) Integer pageSize)  {
		  Pageable pageable = PageRequest.of(pageNumber -1 , pageSize);
		
		 Page<InjectionResult> contentPage= injectionResultService.listResultPagging(pageable);
		 
		 List<InjectionResultListDTO> injectionResultListDTOs  = convertToDTO(contentPage.getContent());
		 List<Integer> list = new ArrayList<>();
		 for (int i = 1; i <= contentPage.getTotalPages(); i++) {
	            list.add(i);
	        }
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("pageNumList", list);
		 map.put("page", contentPage);
		 map.put("injectionResultListDTOs", injectionResultListDTOs);
		 map.put("pageNumber", contentPage.getNumber());
		 return map;
	}
	
	
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
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

