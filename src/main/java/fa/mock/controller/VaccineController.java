package fa.mock.controller;

import fa.mock.entities.Vaccine;
import fa.mock.entities.VaccineType;
import fa.mock.repository.VaccineRepository;
import fa.mock.repository.VaccineTypeRepository;
import fa.mock.service.VaccineService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Path;
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
                                  @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                  @RequestParam(value = "searchTerm", required = false) String searchTerm
    ) {


        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Vaccine> contentPage = null;
        List<Integer> list = new ArrayList<>();

        if (searchTerm == null) {
            contentPage = vaccineRepository.findAll(pageable);

            for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                list.add(i);
            }

            model.addAttribute("searchTerm", null);
            model.addAttribute("pageNumList", list);
            model.addAttribute("list", contentPage);
            model.addAttribute("total", contentPage.getTotalElements());


        } else {
            //       Hiển thị list vaccine khi tìm kiếm

            contentPage = vaccineRepository.findByVaccineType("%" + searchTerm + "%", pageable);


            if (contentPage.getTotalElements() == 0) {
                model.addAttribute("list", null);

            } else {

                for (int i = 1; i <= contentPage.getTotalPages(); i++) {
                    list.add(i);
                }
                model.addAttribute("list", contentPage);

            }
            model.addAttribute("pageNumList", list);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("total", contentPage.getTotalElements());
        }

        model.addAttribute("pageSize", pageSize);


        System.out.println("pageS Get " + pageSize);
        System.out.println("search " + searchTerm);
        return "/vaccinemanagement/vaccinelist";
    }


    @ModelAttribute("vaccinetype")
    public List<VaccineType> vaccineTypeList() {
        return vaccineTypeRepository.findAll();
    }

    @GetMapping("/vaccine-create")
    public String vaccineCreatePage(Model model) {
        model.addAttribute("vaccine", new Vaccine());
        return "/vaccinemanagement/vaccinecreate";
    }

    @Autowired
    HttpServletRequest request;

    @Autowired
    ServletContext servletContext;

    @Async
    @PostMapping("/vaccine-create")
    public String vaccineSavePage(
            @Validated @ModelAttribute("vaccine") Vaccine vaccine,
            BindingResult result, @RequestParam(value = "imageInput", required = false)
            MultipartFile imageInput, Model model
            ) {
        if (result.hasErrors()) {

            return "/vaccinemanagement/vaccinecreate";
        }

        Vaccine vaccineDB = vaccineRepository.findById(vaccine.getId()).orElse(null);
        if (vaccineDB != null) {
            model.addAttribute("message", "Vaccine is already exits");
            return "/vaccinemanagement/vaccinecreate";
        }

        Vaccine vaccineDB2 = vaccineRepository.findVaccineByVaccineName(vaccine.getVaccineName());
        if (vaccineDB2 != null) {
            model.addAttribute("message", "Vaccine Name is already exits");
            return "/vaccinemanagement/vaccinecreate";
        }


        if (!imageInput.isEmpty()) {
            try {
                // Lưu ảnh vào cơ sở dữ liệu hoặc thư mục
                //byte[] imageData = imageInput.getBytes();
                String fileName = saveFile(imageInput);
                vaccine.setImage(fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        vaccineService.save(vaccine);
        return "redirect:/vaccine-list";
    }

    @PostMapping("/vaccine-update")
    public String vaccineUpdatePage(@Validated @ModelAttribute("vaccine") Vaccine vaccine, BindingResult result, @RequestParam(value = "imageInput", required = false) MultipartFile imageInput, Model model) {
        if (result.hasErrors()) {
            System.out.println("lỗi");
            return "/vaccinemanagement/vaccineupdate";
        }

        Vaccine vaccineDB2 = vaccineRepository.findVaccineByVaccineName(vaccine.getVaccineName());
        if (vaccineDB2 != null && !vaccineDB2.getId().equals(vaccine.getId())) {
            model.addAttribute("message", "Vaccine Name is already exits");
            return "/vaccinemanagement/vaccineupdate";
        }

        if (!imageInput.isEmpty()) {
            try {
                // Lưu ảnh vào cơ sở dữ liệu hoặc thư mục
                //byte[] imageData = imageInput.getBytes();

                String fileName = saveFile(imageInput);
                vaccine.setImage(fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Vaccine vaccineDB  = vaccineRepository.findById(vaccine.getId()).orElse(null);
            vaccine.setImage(vaccineDB.getImage());
        }
        System.out.println("ko lỗi");
        vaccineService.save(vaccine);
        return "redirect:/vaccine-list";
    }

    @GetMapping("/vaccine-update")
    public String vaccineUpdatePage(@RequestParam String id, Model model) {
        Vaccine vaccineDB = vaccineRepository.findById(id).orElse(null);
        model.addAttribute("vaccine", vaccineDB);
        return "/vaccinemanagement/vaccineupdate";
    }

    @ResponseBody
    @PostMapping("/vaccine-updatestatus")
    public List<Vaccine> InactiveVaccine(@RequestBody String[] arrId) {

        List<Vaccine> list = new ArrayList<>();
        for (String id : arrId) {
            Vaccine vaccineDb = vaccineRepository.findById(id).orElse(null);
            if (vaccineDb != null) {
                vaccineDb.setStatus(false);
                vaccineRepository.save(vaccineDb);
                list.add(vaccineDb);
            }
        }
        return list;
    }

    @GetMapping("/showImage/{vaccineId}")
    public ResponseEntity<byte[]> showImage(@PathVariable String vaccineId) {
        try {
            byte[] imageData = vaccineRepository.getImageDataById(vaccineId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private String saveFile(MultipartFile imageInput) throws IOException {
        String result = null;
        //byte[] imageData = imageInput.getBytes();
        String fileName = imageInput.getOriginalFilename();
        String destination = "C://temp//" + fileName;
        imageInput.transferTo(Path.of(destination));
        return "/images/" + fileName;
    }

}
