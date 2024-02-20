package fa.mock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class reportVaccineController {

    @GetMapping("/report-vaccine")
    public String report(){

        return "/reporyVaccine";
    }

//    @ResponseBody
//    @PostMapping("/report-vaccine-paging")
////    public Map<String, Object> pagingResult(@RequestBody)
}
