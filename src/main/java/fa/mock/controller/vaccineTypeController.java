package fa.mock.controller;

import fa.mock.DTO.VaccineType.PagingDTO;
import fa.mock.entities.VaccineType;
import fa.mock.repository.VaccineTypeRepository;
import fa.mock.service.VaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @GetMapping(value = {"/vaccine-type-create","/vaccine-type-update"})
    public String createType(ModelMap map, @RequestParam(required = false) String id
    ) {
        VaccineType vaccineType;
        if(id != null){
            vaccineType =vaccineTypeRepository.findById(id).orElse(null);
        }else {
            vaccineType = new VaccineType();
        }
        map.addAttribute("vaccineType", vaccineType);
        return "/vaccineType/typeCreate";
    }

    @PostMapping("/vaccine-type-create")
    public String saveType(@ModelAttribute("vaccineType") VaccineType vaccineType){
        vaccineTypeRepository.save(vaccineType);
        return "redirect:/vaccine-type-list";
    }

    @ResponseBody
    @GetMapping("/vaccine-type-updatestatus")
    public VaccineType InactiveVaccine(@RequestParam String id){
        VaccineType vaccineTypeDb =  vaccineTypeRepository.findById(id).orElse(null);
        if (vaccineTypeDb != null) {
            vaccineTypeDb.setStatus(false);
            vaccineTypeRepository.save(vaccineTypeDb);
            return vaccineTypeDb;
        }
        return null;
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
