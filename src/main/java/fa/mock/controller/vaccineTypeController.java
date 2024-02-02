package fa.mock.controller;

import fa.mock.entities.VaccineType;
import fa.mock.repository.VaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class vaccineTypeController {
    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    @GetMapping("/vaccine-type-list")
    public String listType(ModelMap map){
        map.addAttribute("vaccineTypes", vaccineTypeRepository.findAll());
        return "/vaccineType/typeList";
    }

    @GetMapping("/vaccine-type-create")
    public String createType(ModelMap map) {
        map.addAttribute("vaccineType", new VaccineType());
        return "/vaccineType/typeCreate";
    }

    @PostMapping("/vaccine-type-create")
    public String saveType(@ModelAttribute("vaccineType") VaccineType vaccineType){
        vaccineTypeRepository.save(vaccineType);
        return "redirect:/vaccine-type-list";
    }
}
