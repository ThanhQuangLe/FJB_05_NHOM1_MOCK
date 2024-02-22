package fa.mock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fa.mock.entities.Users;
import fa.mock.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public Users saveUser(Users users) {

		String[] nameRaw =  users.getFullName().split(" ");

		String prefix = "";
		for (String st : nameRaw) {
			prefix += st.substring(0, 1).toUpperCase();
		}

		String maxId = userRepository.getMaxId("%"+prefix+"%");
		String generatedId ="";
		if (maxId != null) {
		        String numericPart = maxId.substring(prefix.length());
		        int numericValue = Integer.parseInt(numericPart);
		        int incrementedValue = numericValue + 1;
		        String newNumericPart = String.format("%04d", incrementedValue);
		        generatedId = prefix + newNumericPart;
		    }else {
		    	generatedId = prefix+"0001";
			}
		users.setId(generatedId);
		userRepository.save(users);
	return users;
	}
}
