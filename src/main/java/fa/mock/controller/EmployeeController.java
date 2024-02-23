package fa.mock.controller;

import fa.mock.entities.InjectionResult;
import fa.mock.entities.RoleEnum;
import fa.mock.entities.Users;
import fa.mock.entities.Vaccine;
import fa.mock.repository.UserRepository;
//import fa.mock.service.EmployeeService;
import fa.mock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    UserRepository userRepository;
//    @Autowired
//    EmployeeService employeeService;

    @Autowired
    UserService userService;

    @GetMapping("/employee-list")
    public String employeeListPage(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                                   @RequestParam(value = "searchTerm", required = false) String searchTerm
    ) {

        Pageable pageable1 = PageRequest.of(pageNumber - 1, pageSize);

        Page<Users> contentPage = null;
        List<Integer> list = new ArrayList<>();

        if (contentPage == null){
            System.out.println("No result found");;
        }

        if (searchTerm == null) {
            contentPage = userRepository.findAllEmployeePaging(pageable1);

            for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                list.add(i);
            }
            model.addAttribute("list", contentPage);

            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("pageNumList", list);
            model.addAttribute("total", contentPage.getTotalElements());

        } else {
            //hien thi list
            List<Users> users = userRepository.findUsersByRole("ROLE_EMPLOYEE");
           // contentPage = users.contains("%" + searchTerm + "%", users);

            if (contentPage.getTotalElements() == 0) {
                model.addAttribute("list", null);
            } else {
                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }
                model.addAttribute("list", contentPage);


                //View list
                if (searchTerm != null) {
                    contentPage = userRepository.findEmployee("%" + searchTerm + "%", pageable1);
                }
                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }

            }
            model.addAttribute("pageNumList", list);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("list", contentPage);
            model.addAttribute("total", contentPage.getTotalElements());
        }

        model.addAttribute("pageSize", pageSize);


        System.out.println("pageS Get " + pageSize);
        System.out.println("search " + searchTerm);
        return "/employeeManagement/employeeList";
    }

    @GetMapping("/employee-update")
    public String employeeUpdatePage(Model model){

        return "/employeeManagement/employeeCreate";
    }

    @ModelAttribute("employee")
    public List<Users> employeeList(){
        return userRepository.findAllEmployee(); //.findUsersByRole("ROLE_EMPLOYEE")
    }

    @GetMapping("/employee-create")
    public String employeeCreatePage(Model model) {
        model.addAttribute("employeeCreate", new Users());
        return "/employeeManagement/employeeCreate";
    }

    @Autowired
    HttpServletRequest request;

    @Autowired
    ServletContext servletContext;
    @Async
    //@EventListener
    @PostMapping("/employee-create")
    public String employeeSavePage(@Validated @ModelAttribute("employeeCreate") Users users, BindingResult result, Model model,
                                   @RequestParam(value = "imageInput",required = false) MultipartFile imageInput) {

        System.out.println(users);
        if (result.hasErrors()) {
            return "/employeeManagement/employeeCreate";
        }
        if (!imageInput.isEmpty()) {
            try {
                // Lưu ảnh vào cơ sở dữ liệu hoặc thư mục
                //byte[] imageData = imageInput.getBytes();
                String fileName = saveFile(imageInput);
                users.setImage(fileName);
//                model.addAttribute("msg", "Uploaded images: " + imageInput.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("===========================da vvao day");
        System.out.println(users);
        users.setRole(RoleEnum.ROLE_EMPLOYEE);
        userService.saveUser(users);
        return "redirect:/employee-list";
    }

//    @GetMapping("/employee-update")
//    public String employeeUpdatePage(@RequestParam String id,Model model) {
//        Users usersDB = userRepository.findById(id).orElse(null);
//        model.addAttribute("users", usersDB);
//        return "/employeeManagement/employeeCreate";
//    }

        @GetMapping("/showImage/{userId}")
        public ResponseEntity<byte[]> showImage(@PathVariable String userId) {
            try {
                byte[] imageData = userRepository.getImageDataById(userId);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
//    private static final String UPLOAD_DIRECTORY = System.getProperty("C:") + "/temp";
    //subdirectory trong thu muc root cua du an tuy tung user voi user.dir
    //hoac la o C than thanh

    private String saveFile(MultipartFile imageInput) throws IOException {
//        StringBuilder fileNames = new StringBuilder();
//        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, imageInput.getOriginalFilename());
//        Files.write(fileNameAndPath, imageInput.getBytes());
//        fileNames.append(imageInput.getOriginalFilename());

        String result = null;
        //byte[] imageData = imageInput.getBytes();
        String fileName = imageInput.getOriginalFilename();
        String destination = "C:/temp/" + fileName; //thu muc tu tao trong o C
        imageInput.transferTo(Path.of(destination));
        return "/images/" + fileName; //images of JDK 21
    }

    @ResponseBody
    @DeleteMapping("/employee-delete")
    public Integer employeeDelete(@RequestBody String[] arrId){

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
    @PostMapping(value = { "/save-employee", "/update-employee" })
    public Users saveCustomer(@RequestBody Users users){
        Users usersDB = userRepository.findUsersByUserName(users.getUserName());
        if(usersDB != null){
            return null;
        }
        users.setRole(RoleEnum.ROLE_EMPLOYEE);
        userService.saveUser(users);
        return users;
    }


}


