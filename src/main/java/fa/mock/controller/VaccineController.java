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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
                                  @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
                                  @RequestParam(value = "searchTerm", required = false) String searchTerm
                              ) {

//        System.out.println("pageSize Get " + pageSize);
//        System.out.println("search " + searchTerm);


        Pageable pageable =PageRequest.of(pageNumber - 1, pageSize);

        Page<Vaccine> contentPage = null;
        List<Integer> list = new ArrayList<>();

        if(searchTerm == null){
            contentPage  = vaccineRepository.findAll( pageable);

            for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                list.add(i);
            }

            model.addAttribute("searchTerm", null);
            model.addAttribute("pageNumList",list);
            model.addAttribute("list", contentPage);
            model.addAttribute("total",   contentPage.getTotalElements());


        } else {
            //       Hiển thị list vaccine khi tìm kiếm

            contentPage = vaccineRepository.findByVaccineType( "%" + searchTerm + "%",pageable);


            if(contentPage.getTotalElements() == 0){
                model.addAttribute("list",null);

            }else {

                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }
                model.addAttribute("list",contentPage);

            }
            model.addAttribute("pageNumList",list);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("total",   contentPage.getTotalElements());
        }

        model.addAttribute("pageSize", pageSize);


        System.out.println("pageS Get " + pageSize);
        System.out.println("search " + searchTerm);


//        model.addAttribute("pageNumList",list);
//        model.addAttribute("list", contentPage);
//        model.addAttribute("total",   contentPage.getTotalElements());
//        model.addAttribute("pageSize", pageSize);
//        System.out.println("pageSize = " + pageSize);

        return "/vaccinemanagement/vaccinelist";
    }

    //       Hiển thị list vaccine khi tìm kiếm
//    @PostMapping("/vaccine-list")
//    public String handleSearchRequest(@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
//                                      @RequestParam(value = "pageSize", required = false)Integer pageSize,
//                                      @RequestParam("searchTerm") String searchTerm,
//                                      Model model, RedirectAttributes redirectAttributes) {
//
////        Integer pageS = pageSize != 5 ? pageSize : 5;
////        System.out.println("pageSize Post " + pageS);
////        Pageable pageable =PageRequest.of(pageNumber - 1, pageS);
//
//        System.out.println("pageSize Post " + pageSize);
//        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
//
//        Page<Vaccine> contentPage = null;
//        List<Integer> list = new ArrayList<>();
//
//        if(searchTerm == ""){
//            redirectAttributes.addFlashAttribute("pageSize", pageSize);
//           return "redirect:/vaccine-list";
//        }else {
//            contentPage = vaccineRepository.findByVaccineType("%" + searchTerm + "%" ,pageable);
//
//
//            if(contentPage.getTotalElements() == 0){
//                model.addAttribute("list",null);
//
//            }else {
//
//                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
//                    list.add(i);
//                }
//
//                model.addAttribute("pageNumList",list);
//                model.addAttribute("list",contentPage);
//                model.addAttribute("total",   contentPage.getTotalElements());
//                model.addAttribute("searchTerm", searchTerm);
//                model.addAttribute("pageSize", pageSize);
//            }
//        }
//        return "/vaccinemanagement/vaccinelist";
//    }

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
