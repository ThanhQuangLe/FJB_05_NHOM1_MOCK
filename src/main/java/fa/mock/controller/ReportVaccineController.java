package fa.mock.controller;

import fa.mock.DTO.ReportVaccine.VaccineDTO;
import fa.mock.entities.VaccineType;
import fa.mock.repository.VaccineRepository;
import fa.mock.repository.VaccineTypeRepository;
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
public class ReportVaccineController {

    @Autowired
    VaccineRepository vaccineRepository;

    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    @GetMapping("/report-vaccine")
    public String reportVaccinePage(Model model){
        model.addAttribute("vaccineDTO", new VaccineDTO());
        return "/Report/reportVaccine";
    }

    @PostMapping("/report-vaccine")
    public String reportVaccinePage(@ModelAttribute VaccineDTO vaccineDTO, Model model){

        LocalDate beginDate = vaccineDTO.getTimeBeginNextInjection() !=null ? vaccineDTO.getTimeBeginNextInjection() : LocalDate.of(1900, 1, 1);
        LocalDate endDate = vaccineDTO.getTimeEndNextInjection() != null ? vaccineDTO.getTimeEndNextInjection() : LocalDate.of(9999, 1, 1);
        String origin = vaccineDTO.getOrigin();
        String vaccineTypeId = vaccineDTO.getVaccineTypeId();

        Integer pageNumber;
        if(vaccineDTO.getPageNumber() !=null){
            pageNumber = vaccineDTO.getPageNumber();
        }else {
            pageNumber =1;
        }
        Integer pageSize = vaccineDTO.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<Object[]> contentPage = vaccineRepository.findVaccineForReport("%" + origin+ "%", "%" + vaccineTypeId + "%" , beginDate, endDate, pageable);

//        Page<Object[]> contentPage;
//        if(vaccineTypeId != "" || origin != ""){
//            contentPage = vaccineRepository.findVaccineForReport("%" + origin+ "%",vaccineTypeId , beginDate, endDate, pageable);
//        }else {
//            contentPage = vaccineRepository.findVaccineFor3Report("%" + origin+ "%", beginDate, endDate, pageable);
//        }
//        List<Object[]> objects = contentPage.getContent();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= contentPage.getTotalPages(); i++) {
            list.add(i);
        }
        model.addAttribute("pageNumList",list);
        model.addAttribute("list", contentPage);
        model.addAttribute("total", contentPage.getTotalElements());
        model.addAttribute("vaccineDTO", vaccineDTO);

        return "/Report/reportVaccine";
    }
    @ModelAttribute("vaccineType")
    public List<VaccineType> vaccineTypeList(){
        return vaccineTypeRepository.findAll();
    }

    @ResponseBody
    @PostMapping("/reportVaccine")
    public Integer[] chartVaccine(@RequestBody Integer year){
        Integer result[] = new Integer[12];
        for (int i = 1; i <=12 ; i++) {
            Integer count = vaccineRepository.findVaccineName(year, i);
            result[i-1] = count;
        }
        return result;
    }
}
