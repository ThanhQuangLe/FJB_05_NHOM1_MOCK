package fa.mock.controller;

import fa.mock.DTO.Customer.CustomerDTO;
import fa.mock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "/Report/reportCustomer";
    }

    @PostMapping("/report-customer")
    public String reportCustomer(@ModelAttribute CustomerDTO customerDTO,Model model){
//        System.out.println("Chạy postmaping");

        LocalDate dateFrom = (customerDTO.getDateOfBirthFrom() != null) ? customerDTO.getDateOfBirthFrom() : LocalDate.of(1900, 1, 1);
        LocalDate dateTo = (customerDTO.getDateOfBirthTo() != null) ? customerDTO.getDateOfBirthTo() : LocalDate.now();
        String fullName = customerDTO.getFullName();
        String address = customerDTO.getAddress();

        Integer pageNumber;
        if(customerDTO.getPageNumber() != null){
            pageNumber = customerDTO.getPageNumber();
        }else {
            pageNumber =1;
        }

//        System.out.println("pageNumber"+ pageNumber);
        Integer pageSize = customerDTO.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Object[]> contentPage = userRepository.findUsersForReport(dateFrom,dateTo,"%" +fullName+ "%","%" +address+ "%",pageable);

        if(contentPage.getTotalElements() >0){
            model.addAttribute("list", contentPage);
        }else {
            model.addAttribute("list", null);
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= contentPage.getTotalPages(); i++) {
            list.add(i);
        }

            model.addAttribute("pageNumList",list);
            model.addAttribute("total",   contentPage.getTotalElements());
            model.addAttribute("customerDTO", customerDTO);

        return "/Report/reportCustomer";
    }

    @ResponseBody
    @PostMapping("/reportcustomer")
    public Integer[] chartCustomer(@RequestBody Integer year){
        Integer[] result = new Integer[12];
        for (int i = 1; i <= 12; i++){
          Integer count = userRepository.findCustomerForReport(year, i);
            result[i-1] = count;

        }
        return result;
    }
}
