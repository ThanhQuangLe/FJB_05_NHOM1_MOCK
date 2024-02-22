package fa.mock.controller;

import fa.mock.entities.Users;
import fa.mock.entities.Vaccine;
import fa.mock.repository.UserRepository;
import fa.mock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fa.mock.entities.RoleEnum.ROLE_USER;

@Controller
public class CustomerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    @GetMapping("/customer-list")
    public String customerListPage(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                                   @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
                                   @RequestParam(value = "searchTerm", required = false) String searchTerm){

        Pageable pageable =PageRequest.of(pageNumber - 1, pageSize);

        Page<Users> contentPage = null;
        List<Integer> list = new ArrayList<>();




        if(searchTerm == null){
            contentPage  = userRepository.findAllCustomerPaging( pageable);

            if(pageNumber > contentPage.getTotalPages()){
                pageNumber = contentPage.getTotalPages();
            }

            for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                list.add(i);
            }

            model.addAttribute("searchTerm", null);
            model.addAttribute("pageNumList",list);
            model.addAttribute("list", contentPage);
            model.addAttribute("total",   contentPage.getTotalElements());

        } else {
            //       Hiển thị list vaccine khi tìm kiếm

            contentPage = userRepository.findUsers( "%" + searchTerm + "%",pageable);
            if(pageNumber > contentPage.getTotalPages()){
                pageNumber = contentPage.getTotalPages();
            }

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

        return "/customermanagement/customerlist";
    }

//    @GetMapping("/customer-list")
//    public String customerListPage(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
//                                   @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize){
//
//        Pageable pageable =PageRequest.of(pageNumber - 1, pageSize);
//
//        Page<Users> contentPage = userRepository.findAllCustomerPaging(pageable);
//        List<Integer> list = new ArrayList<>();
//        for (int i = 1; i <= contentPage.getTotalPages(); i++) {
//            list.add(i);
//        }
//
//            model.addAttribute("pageNumList",list);
//            model.addAttribute("list", contentPage);
//            model.addAttribute("total",   contentPage.getTotalElements());
//            model.addAttribute("pageSize", pageSize);
//
//        return "/customermanagement/customerlist";
//    }


    @GetMapping("/customer-update")
    public String customerUpdatePage(Model model){

        return "/customermanagement/customerupdate";
    }

    @GetMapping("/customer-create")
    public String customerCreatePage(Model model){

        return "/customermanagement/customercreate";
    }


    @ResponseBody
    @DeleteMapping("/customer-delete")
    public Integer customerDelete(@RequestBody String[] arrId){

        Integer count = 0;
        for (String id : arrId) {
            Users usersDb =  userRepository.findById(id).orElse(null);
            if(usersDb != null){

                userRepository.delete(usersDb);
                count ++;
            }
        }
        return count;
    }

    @ResponseBody
    @PostMapping("/save-customer")
    public String saveCustomer(@RequestBody Users users){

        Users usersDB = userRepository.findUsersByUserName(users.getUserName());
        if(usersDB != null){
            return "username";
        }

        Users usersDB2 = userRepository.findUsersByEmail(users.getEmail());
        if(usersDB2 != null) {
            return "email";
        }

        Users usersDB3 = userRepository.findUsersByPhone(users.getPhone());
        if(usersDB3 != null) {
            return "phone";
        }

        users.setRole(ROLE_USER);
        userService.saveUser(users);
        return "success";
    }

    @ResponseBody
    @PostMapping("/update-customer")
    public String updateCustomer(@RequestBody Users users){

        Users usersDB = userRepository.findUsersByUserName(users.getUserName());
        if( usersDB != null && !users.getId().equals(usersDB.getId())){
            return "username";
        }


        Users usersDB2 = userRepository.findUsersByEmail(users.getEmail());
        if(usersDB2 != null && !users.getId().equals(usersDB2.getId())) {
            return "email";
        }

        Users usersDB3 = userRepository.findUsersByPhone(users.getPhone());
        if(usersDB3 != null && !users.getId().equals(usersDB3.getId())) {
            return "phone";
        }

        users.setRole(ROLE_USER);
        userRepository.save(users);
        return "success";
    }

    @ResponseBody
    @GetMapping("/customer-get")
    public Users getCustomer(@RequestParam String id){
        Users users = userRepository.findById(id).orElse(null);
        return users;
    }


}
