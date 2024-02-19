package fa.mock.controller;

import fa.mock.entities.InjectionSchedule;
import fa.mock.entities.Users;
import fa.mock.entities.Vaccine;
import fa.mock.repository.InjectionScheduleRepository;
import fa.mock.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VaccineScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    InjectionScheduleRepository injectionScheduleRepository;

    @GetMapping("/schedule-list")
    public String scheduleListPage(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                   @RequestParam(value = "searchTerm", required = false) String searchTerm
    ) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<InjectionSchedule> contentPage = null;
        List<Integer> list = new ArrayList<>();


//        if (searchTerm == null) {
//            return "redirect:/schedule-list";
//        } else {
            contentPage = injectionScheduleRepository.findAll(pageable);

            if (contentPage.getTotalElements() == 0) {
                model.addAttribute("list", null);
            } else {
                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }


                //View list
                if (searchTerm != null) {
                    contentPage = injectionScheduleRepository.findById("%" + searchTerm + "%", pageable);
                }

                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }
                model.addAttribute("searchTerm", searchTerm);
                model.addAttribute("pageNumList",list);
                model.addAttribute("list",contentPage);
                model.addAttribute("total",contentPage.getTotalElements());
            }

            return "/vaccineSchedule/scheduleList";
//        }
    }



    @ModelAttribute("schedule")
    public List<InjectionSchedule> scheduleList(){
        return injectionScheduleRepository.findAll();
    }

    @GetMapping("/schedule-create")
    public String scheduleCreatePage(Model model) {
        model.addAttribute("schedule", new InjectionSchedule());
        return "/vaccineSchedule/scheduleCreate";
    }

    @PostMapping("/schedule-create")
    public String scheduleSavePage(@Validated @ModelAttribute("schedule") InjectionSchedule injectionSchedule, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "/vaccineSchedule/scheduleCreate";
        }
        scheduleService.save(injectionSchedule);
        return "redirect:/schedule-list";
    }

//    @PostMapping("/schedule-update")
//    public String scheduleUpdatePage(@RequestParam InjectionSchedule injectionSchedule, Model model) {
//        InjectionSchedule injectionScheduleDB = injectionScheduleRepository.findById(injectionSchedule.getId()).orElse(null);
//        model.addAttribute("injectionSchedule", injectionScheduleDB);
//        injectionScheduleRepository.save(injectionSchedule);
//        return "redirect:/schedule-list";
//
//    }

}


