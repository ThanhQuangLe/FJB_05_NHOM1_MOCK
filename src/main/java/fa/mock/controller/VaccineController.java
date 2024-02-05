package fa.mock.controller;

import fa.mock.entities.Vaccine;
import fa.mock.entities.VaccineType;
import fa.mock.repository.VaccineRepository;
import fa.mock.repository.VaccineTypeRepository;
import fa.mock.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;

@Controller
public class VaccineController {

    @Autowired
    VaccineService vaccineService;

    @Autowired
    VaccineRepository vaccineRepository;

    @Autowired
    VaccineTypeRepository vaccineTypeRepository;
    @GetMapping("/vaccine-list")
    public String vaccineListPage(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                                  @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                  @RequestParam(value = "searchTerm", required = false) String searchTerm
                              ) {

        Pageable pageable =PageRequest.of(pageNumber - 1, pageSize);

        Page<Vaccine> contentPage = null;
        List<Integer> list = new ArrayList<>();


        if(searchTerm == null){
            contentPage  = vaccineRepository.findAll( pageable);

            for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                list.add(i);
            }

            model.addAttribute("searchTerm", null);
        } else {
            //       Hiển thị list vaccine khi tìm kiếm

            contentPage = vaccineRepository.findByVaccineType( "%" + searchTerm + "%",pageable);

            for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                list.add(i);
            }

            model.addAttribute("searchTerm", searchTerm);

        }

        model.addAttribute("pageNumList",list);
        model.addAttribute("list", contentPage);
        model.addAttribute("total",   contentPage.getTotalElements());


        return "/vaccinemanagement/vaccinelist";
    }

    //       Hiển thị list vaccine khi tìm kiếm
    @PostMapping("/vaccine-list")
    public String handleSearchRequest(@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
                                      @RequestParam(value = "pageSize", defaultValue = "5", required = false)Integer pageSize,
                                      @RequestParam("searchTerm") String searchTerm,
                                      Model model) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Vaccine> contentPage = null;
        List<Integer> list = new ArrayList<>();

        if(searchTerm == ""){
           return "redirect:/vaccine-list";
        }else {
            contentPage = vaccineRepository.findByVaccineType("%" + searchTerm + "%" ,pageable);


            if(contentPage.getTotalElements() == 0){
                model.addAttribute("list",null);

            }else {

                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }

                model.addAttribute("pageNumList",list);
                model.addAttribute("list",contentPage);
                model.addAttribute("total",   contentPage.getTotalElements());
                model.addAttribute("searchTerm", searchTerm);

            }
        }

        return "/vaccinemanagement/vaccinelist";
    }

    @ModelAttribute("vaccinetype")
    public List<VaccineType> vaccineTypeList(){
        return vaccineTypeRepository.findAll();
    }

    @GetMapping("/vaccine-create")
    public String vaccineCreatePage(Model model) {
        model.addAttribute("vaccine", new Vaccine());
        return "/vaccinemanagement/vaccinecreate";
    }

    @PostMapping("/vaccine-create")
    public String vaccineSavePage(@Validated @ModelAttribute("vaccine") Vaccine vaccine, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "/vaccinemanagement/vaccinecreate";
        }
        vaccineService.save(vaccine);
        return "redirect:/vaccine-list";
    }

    @GetMapping("/vaccine-update")
    public String vaccineUpdatePage(@RequestParam String id,Model model) {
        Vaccine vaccineDB = vaccineRepository.findById(id).orElse(null);
        model.addAttribute("vaccine", vaccineDB);
        return "/vaccinemanagement/vaccineupdate";
    }

    @ResponseBody
    @PostMapping("/vaccine-updatestatus")
    public List<Vaccine> InactiveVaccine(@RequestBody String[] arrId){

        List<Vaccine> list = new ArrayList<>();
        for (String id : arrId) {
            Vaccine vaccineDb =  vaccineRepository.findById(id).orElse(null);
            if (vaccineDb != null) {
                vaccineDb.setStatus(false);
                vaccineRepository.save(vaccineDb);
               list.add(vaccineDb);
            }

        }


        return list;

//        Vaccine vaccineDb =  vaccineRepository.findById(id).orElse(null);
//        if (vaccineDb != null) {
//            vaccineDb.setStatus(false);
//            vaccineRepository.save(vaccineDb);
//            return vaccineDb;
//        }
//       return null;
    }
}
