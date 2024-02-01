package fa.mock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fa.mock.entities.Users;
import fa.mock.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = {"/","/home"})
	public String homeUI() {
	       
        Users users = new Users();
        users.setEmail("name@facd.com");
        
        userRepository.save(users);
		return "html";
	}
}
