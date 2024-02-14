package fa.mock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
    @GetMapping("/employee")
    public String employeeListPage(Model model){
        return "/employeeManagement/employeeList";
    }
    @GetMapping("/news")
    public String newsListPage(Model model){
        return "/news/listNews";
    }
    @GetMapping("/vaccine-schedule")
    public String scheduleListPage(Model model){
        return "/vaccineSchedule/scheduleList";
    }
}
