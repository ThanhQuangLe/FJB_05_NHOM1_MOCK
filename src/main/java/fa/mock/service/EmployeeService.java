package fa.mock.service;

import fa.mock.entities.Users;
import fa.mock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    UserRepository userRepository;

    public Users save(Users users){
        userRepository.save(users);
        return users;
    }

}
