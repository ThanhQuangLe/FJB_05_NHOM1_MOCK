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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.mock.DTO.VaccineResult.InjectionResultDTO;
import fa.mock.DTO.VaccineResult.InjectionResultListDTO;
import fa.mock.DTO.VaccineResult.PagingDTO;
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

	@GetMapping(value = { "/vaccineResult-create", "/vaccineResult-update/{id}" })
	public String showCreateResultUi(Model model, @PathVariable(value = "id", required = false) Integer id,
			@ModelAttribute("notification") String noti,
			@ModelAttribute("failDTO") InjectionResultDTO injectionResultDTO) {
		List<Users> users = userRepository.findAll();
		List<Vaccine> vaccines = vaccineRepository.findAll();
		List<InjectionSchedule> injectionSchedules = injectionScheduleRepository.findAll();
		model.addAttribute("notification", noti);
		model.addAttribute("users", users);
		model.addAttribute("vaccines", vaccines);
		model.addAttribute("injectionSchedules", injectionSchedules);

		if (id == null) {
			model.addAttribute("resultDTO", new InjectionResultDTO());
			if (injectionResultDTO.getUserId() != null) {
				model.addAttribute("resultDTO", injectionResultDTO);
			}
		} else {
				InjectionResult injectionResult = injectionResultRepository.findById(id).orElse(null);
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
	public String showListResultUi(Model model, @ModelAttribute("notification") String noti) {
		model.addAttribute("notification", noti);
		Pageable pageable = PageRequest.of(0, 5);
		Page<InjectionResult> contentPage = injectionResultService.listResultPagging(pageable);
		List<InjectionResultListDTO> injectionResultListDTOs = convertToDTO(contentPage.getContent());
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= contentPage.getTotalPages(); i++) {
			list.add(i);
		}
		model.addAttribute("injectionResultListDTOs", injectionResultListDTOs);
		model.addAttribute("page", contentPage);
		model.addAttribute("pageNumList", list);
		return "/vaccineResult/ResultList";
	}

	@PostMapping("/vaccineResult-create")
	public String createResult(@ModelAttribute("resultDTO") InjectionResultDTO injectionResultDTO,
			RedirectAttributes attributes) {
		if (injectionResultService.saveResult(injectionResultDTO) == null) {
			attributes.addFlashAttribute("notification",
					"The Result is not correct, the customer is already in another result");
			if (injectionResultDTO.getId() != null) {
				return "redirect:/vaccineResult-update/" + injectionResultDTO.getId();
			} else {
				attributes.addFlashAttribute("failDTO", injectionResultDTO);
				return "redirect:/vaccineResult-create";
			}

		}
		attributes.addFlashAttribute("notification", "Successful operation");

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
		Pageable pageable = PageRequest.of(0, 5);
		String searchKey = input.replace("\"", "");
		Page<InjectionResult> page = injectionResultService.getResult(searchKey, pageable);
		List<InjectionResultListDTO> result = convertToDTO(page.getContent());
		return result;
	}

	@PostMapping("/vaccineResult-paging")
	@ResponseBody
	public Map<String, Object> pagingResult(@RequestBody PagingDTO dto) {
		String searchKey = dto.getInput().replace("\"", "");

		int pageNum = Integer.parseInt(dto.getPageNum().replace("\"", ""));

		int pageSize = Integer.parseInt(dto.getPageSize().replace("\"", ""));

		Pageable pageableCheck = PageRequest.of(0, pageSize);

		Page<InjectionResult> contentPageCheck = injectionResultService.getResult(searchKey, pageableCheck);

		int checkPage = contentPageCheck.getTotalPages();

		Pageable pageable;

		if (checkPage == 0) {
			pageable = PageRequest.of(checkPage, pageSize);
			pageNum = checkPage;
		} else if (pageNum > checkPage) {
			pageNum = checkPage - 1;
			pageable = PageRequest.of(pageNum, pageSize);

		} else {
			pageable = PageRequest.of(pageNum - 1, pageSize);
		}

		Page<InjectionResult> contentPage = injectionResultService.getResult(searchKey, pageable);

		List<InjectionResultListDTO> injectionResultListDTOs = convertToDTO(contentPage.getContent());

		List<Integer> list = new ArrayList<>();

		for (int i = 1; i <= contentPage.getTotalPages(); i++) {
			list.add(i);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("injectionResultListDTOs", injectionResultListDTOs);
		result.put("pageNumber", pageNum - 1);
		result.put("hasPrevious", contentPage.hasPrevious());
		result.put("hasNext", contentPage.hasNext());
		result.put("pageSize", pageSize);
		result.put("total", contentPage.getTotalElements());
		return result;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////

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
