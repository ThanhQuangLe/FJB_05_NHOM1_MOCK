package fa.mock.auth;

import fa.mock.entities.Users;
import fa.mock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users usersDB = userRepository.findByUserName(username);
        if(usersDB == null) {
            throw new UsernameNotFoundException("Username is not found");
        }

        UsersDetail userDetail = new UsersDetail();
        userDetail.setUsers(usersDB);
        return userDetail;
    }
}
