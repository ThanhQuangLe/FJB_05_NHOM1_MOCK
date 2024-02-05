package fa.mock.controller;

import fa.mock.entities.VaccineType;
import fa.mock.repository.VaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class vaccineTypeController {
    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

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
        map.addAttribute("list",contentPage);
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

//    @GetMapping("/vaccine-type-update/{id}")
//    public String updateType(ModelMap map, @PathVariable String id, RedirectAttributes redirectAttributes){
//        redirectAttributes.addFlashAttribute("vaccineType", vaccineTypeRepository.findById(id).orElse(null));
//        return "redirect:/vaccine-type-create";
//    }

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
}
