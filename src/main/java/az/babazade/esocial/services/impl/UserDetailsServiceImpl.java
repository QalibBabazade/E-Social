package az.babazade.esocial.services.impl;


import az.babazade.esocial.entities.User;
import az.babazade.esocial.enums.EnumAvailableStatus;
import az.babazade.esocial.exception.ExceptionConstant;
import az.babazade.esocial.exception.MyException;
import az.babazade.esocial.repositories.UserRepository;
import az.babazade.esocial.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null){
            throw new MyException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid request data!");
        }
        User user = userRepository.findUserByUsernameAndActive(username, EnumAvailableStatus.ACTIVE.getValue());
        if(user == null){
            throw new MyException(ExceptionConstant.USER_NOT_FOUND,"User not found!");
        }
        return JwtUserDetails.create(user);
    }

}
