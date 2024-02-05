package fa.mock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
    @GetMapping("/customer-list")
    public String customerListPage(Model model){

        return "/customermanagement/customerlist";
    }

}
