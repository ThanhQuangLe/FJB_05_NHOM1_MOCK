package fa.mock.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import fa.mock.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@GetMapping(value = { "/home", "/" })
	public String homeUI() {
		return "/Dashboard/home";
	}

	@GetMapping("/login")
	public String getLoginFrom(){
		return "login";
	}



}
