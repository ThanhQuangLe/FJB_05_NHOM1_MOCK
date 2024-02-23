package fa.mock.controller;

import fa.mock.DTO.VaccineType.PagingDTO;
import fa.mock.entities.Vaccine;
import fa.mock.entities.VaccineType;
import fa.mock.repository.VaccineRepository;
import fa.mock.repository.VaccineTypeRepository;
import fa.mock.service.VaccineService;
import fa.mock.service.VaccineTypeService;
import fa.mock.service.VaccineTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class vaccineTypeController {
    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    @Autowired
    VaccineTypeService vaccineTypeService;

    @Autowired
    VaccineRepository vaccineRepository;


    @GetMapping("/vaccine-type-list")
    public String listType(ModelMap map,
                           @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);

        Page<VaccineType> contentPage = vaccineTypeRepository.findAll(pageable);
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= contentPage.getTotalPages(); i++) {
            list.add(i);
        }

        map.addAttribute("pageNumList",list);
        map.addAttribute("list", contentPage);
        map.addAttribute("total",contentPage.getTotalElements());

//        map.addAttribute("vaccineTypes", vaccineTypeRepository.findAll());
        return "/vaccineType/typeList";
    }



    @GetMapping("/vaccine-type-create")
    public String createType( ModelMap map) {
        map.addAttribute("vaccineType", new VaccineType());
        return "/vaccineType/typeCreate";
    }

    @Async
    @PostMapping("/vaccine-type-create")
    public String saveType(@Validated @ModelAttribute("vaccineType") VaccineType vaccineType,  BindingResult result,  Model model){

        if (result.hasErrors()) {
            return "/vaccineType/typeCreate";
        }

        VaccineType vaccineTypeĐB = vaccineTypeRepository.findById(vaccineType.getId()).orElse(null);
        VaccineType vaccineTypeDB2 = vaccineTypeRepository.findByVaccineTypeName(vaccineType.getVaccineTypeName());
        if(vaccineTypeĐB !=null){
            model.addAttribute("messagge", "Vaccine Type id is already exits");
            if(vaccineTypeDB2 != null){
                model.addAttribute("messagge1", "Vaccine Type Name already exits");
                return "/vaccineType/typeCreate";
            }
            return "/vaccineType/typeCreate";
        }


        if(vaccineTypeDB2 != null){
            model.addAttribute("messagge", "Vaccine Type Name already exits");

            return "/vaccineType/typeCreate";
        }

        vaccineTypeRepository.save(vaccineType);

        if(vaccineType.getStatus()){
            vaccineRepository.updateVaccinesStatusTrue(vaccineType.getId());
        }else {
            vaccineRepository.updateVaccinesStatusFalse(vaccineType.getId());
        }
        return "redirect:/vaccine-type-list";
    }

    @GetMapping("/vaccine-type-update")
    public String updateType(@RequestParam String id, Model model){
        VaccineType vaccineTypeDB = vaccineTypeRepository.findById(id).orElse(null);
        model.addAttribute("vaccineType", vaccineTypeDB);
        return "/vaccineType/typeUpdate";
    }

    @PostMapping("/vaccine-type-update")
    public String updateType(@Validated @ModelAttribute("vaccineType") VaccineType vaccineType,
                             BindingResult result,  Model model){
        if (result.hasErrors()) {
            return "/vaccineType/typeCreate";
        }
        VaccineType vaccineTypeDB2 = vaccineTypeRepository.findByVaccineTypeName(vaccineType.getVaccineTypeName());
        if(vaccineTypeDB2 != null && !vaccineTypeDB2.getId().equals(vaccineType.getId())){
            model.addAttribute("messagge", "Vaccine Type Name already exits");

            return "/vaccineType/typeCreate";
        }

        vaccineTypeRepository.save(vaccineType);
        if(vaccineType.getStatus()){
            vaccineRepository.updateVaccinesStatusTrue(vaccineType.getId());
        }else {
            vaccineRepository.updateVaccinesStatusFalse(vaccineType.getId());
        }
        return "redirect:/vaccine-type-list";
    }

    @ResponseBody
    @PostMapping("/vaccine-type-updatestatus")
    public List<VaccineType> InactiveVaccine(@RequestBody String[] arrId) {
        List<VaccineType> list = new ArrayList<>();

        for (String id : arrId) {
            VaccineType vaccineTypeDb = vaccineTypeRepository.findById(id).orElse(null);
            if (vaccineTypeDb != null) {
                vaccineTypeDb.setStatus(false);
                vaccineTypeRepository.save(vaccineTypeDb);
                list.add(vaccineTypeDb);
            }
        }
        return list;
    }


    @ResponseBody
    @PostMapping("/vaccine-type-paging")
    public Map<String, Object> pagingResult(@RequestBody PagingDTO pagingDTO){
        System.out.println(pagingDTO);
        String searchKey = pagingDTO.getInput();
        int pageNum = Integer.parseInt(pagingDTO.getPageNum());
        int pageSize = Integer.parseInt(pagingDTO.getPageSize());
        Pageable pageableCheck = PageRequest.of(0, pageSize);
        Page<VaccineType> contentPageCheck = vaccineTypeService.getResult(searchKey, pageableCheck);
        System.out.println(contentPageCheck);
        int checkPage = contentPageCheck.getTotalPages();
        Pageable pageable;
        if(checkPage==0) {
            pageable = PageRequest.of(checkPage, pageSize);
            pageNum = checkPage;
        } else if(pageNum > checkPage){
            pageable = PageRequest.of(checkPage -1, pageSize);
            pageNum = checkPage -1;
        }else {
            pageable = PageRequest.of(pageNum - 1, pageSize);
        }

        Page<VaccineType> contentPage = vaccineTypeService.getResult(searchKey, pageable);
        List<VaccineType> vaccineTypeList = contentPage.getContent();

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= contentPage.getTotalPages(); i++) {
            list.add(i);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("lists", list);
        result.put("vaccineTypeList", vaccineTypeList);
        result.put("pageNumber",  pageNum-1);
        result.put("hasPrevious", contentPage.hasPrevious());
        result.put("hasNext", contentPage.hasNext());
        result.put("pageSize", pageSize);
        result.put("totals", contentPage.getTotalElements());

        return result;
    }

}
