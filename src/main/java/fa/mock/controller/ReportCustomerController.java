package fa.mock.controller;

import fa.mock.DTO.Customer.CustomerDTO;
import fa.mock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportCustomerController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/report-customer")
    public String reportCustomerPage(Model model){
        model.addAttribute("customerDTO", new CustomerDTO());
        return "/reportCustomer";
    }

    @PostMapping("/report-customer")
    public String reportCustomer(@ModelAttribute CustomerDTO customerDTO,Model model){
        System.out.println("Chạy postmaping");

        LocalDate dateFrom = customerDTO.getDateOfBirthFrom();
        LocalDate dateTo = customerDTO.getDateOfBirthTo();
        String fullName = customerDTO.getFullName();
        String address = customerDTO.getAddress();

        Integer pageNumber;
        if(customerDTO.getPageNumber() != null){
            pageNumber = customerDTO.getPageNumber();
        }else {
            pageNumber =1;
        }

        System.out.println("pageNumber"+ pageNumber);
        Integer pageSize = customerDTO.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Object[]> contentPage = userRepository.findUsersForReport(dateFrom,dateTo,"%" +fullName+ "%","%" +address+ "%",pageable);


        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= contentPage.getTotalPages(); i++) {
            list.add(i);
        }

            model.addAttribute("pageNumList",list);
            model.addAttribute("list", contentPage);
            model.addAttribute("total",   contentPage.getTotalElements());
              model.addAttribute("customerDTO", customerDTO);

        return "/reportCustomer";
    }
}
