package fa.mock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fa.mock.entities.Users;
import fa.mock.repository.UserRepository;
import fa.mock.service.UserService;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@GetMapping(value = { "/", "/home" })
	public String homeUI() {

		Users users = new Users();
		users.setFullName("Lê Đức Nam");

		userService.saveUser(users);
		return "/vaccineType/typeList";
	}




}
