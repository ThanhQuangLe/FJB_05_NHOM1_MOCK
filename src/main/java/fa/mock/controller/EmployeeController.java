package fa.mock.controller;

import fa.mock.entities.InjectionResult;
import fa.mock.entities.Users;
import fa.mock.entities.Vaccine;
import fa.mock.repository.UserRepository;
import fa.mock.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employee-list")
    public String employeeListPage(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                   @RequestParam(value = "searchTerm", required = false) String searchTerm
    ) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Users> contentPage = null;
        List<Integer> list = new ArrayList<>();


//        if (searchTerm == null) {
//            return "redirect:/employee-list";
//        } else {
            contentPage = userRepository.findAll(pageable);

            if (contentPage.getTotalElements() == 0) {
                model.addAttribute("list", null);
            } else {
                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }


                //View list
                if(searchTerm != null) {
                    contentPage = userRepository.findById("%" + searchTerm + "%", pageable);
                }
               for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                   list.add(i);
               }
                model.addAttribute("searchTerm", searchTerm);
                model.addAttribute("pageNumList",list);
                model.addAttribute("list",contentPage);
                model.addAttribute("total",contentPage.getTotalElements());
            }

            return "/employeeManagement/employeeList";
        //}
    }



    @ModelAttribute("employee")
    public List<Users> employeeList(){
        return userRepository.findAll();
    }

    @GetMapping("/employee-create")
    public String employeeCreatePage(Model model) {
        model.addAttribute("employee", new Users());
        return "/employeeManagement/employeeCreate";
    }

    @PostMapping("/employee-create")
    public String employeeSavePage(@Validated @ModelAttribute("employee") Users users, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "/employeeManagement/employeeCreate";
        }
        employeeService.save(users);
        return "redirect:/employee-list";
    }

//    @GetMapping("/employee-update")
//    public String employeeUpdatePage(@RequestParam String id,Model model) {
//        Users usersDB = userRepository.findById(id).orElse(null);
//        model.addAttribute("users", usersDB);
//        return "/employeeManagement/employeeCreate";
//    }

}


