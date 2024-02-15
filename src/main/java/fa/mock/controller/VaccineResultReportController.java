package fa.mock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VaccineResultReportController {
	
	@GetMapping("/VaccineResult-Report")
	public String getResultUI() {
		return "/vaccineResultReport/ReportResultList";
	}
}
