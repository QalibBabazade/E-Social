package az.babazade.esocial.services.impl;

import az.babazade.esocial.dto.requests.UserRequest;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.dto.responses.StatusResponse;
import az.babazade.esocial.dto.responses.UserResponse;
import az.babazade.esocial.entities.User;
import az.babazade.esocial.enums.EnumAvailableStatus;
import az.babazade.esocial.exception.ExceptionConstant;
import az.babazade.esocial.exception.MyException;
import az.babazade.esocial.repositories.UserRepository;
import az.babazade.esocial.services.UserService;
import az.babazade.esocial.util.Utility;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private Utility utility;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,Utility utility) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.utility = utility;
    }

    @Override
    public Response<List<UserResponse>> getAllUsers() {
        Response<List<UserResponse>> response = new Response<>();

        try {
            List<User> userList = userRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if(userList == null){
                throw new MyException(ExceptionConstant.USER_NOT_FOUND,"User not found!");
            }
            List<UserResponse> userResponseList = userList.stream().map(UserResponse::new).collect(Collectors.toList());
            response.setT(userResponseList);
            response.setStatusResponse(StatusResponse.getSuccessMessage());
        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }

        return response;
    }

    @Override
    public Response<UserResponse> getUserById(Long userId) {
        Response<UserResponse> response = new Response<>();
        try {
            Optional<User> optionalUser = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.getValue());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                UserResponse userResponse = new UserResponse(user);
                response.setT(userResponse);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            } else {
                throw new MyException(ExceptionConstant.USER_NOT_FOUND,"User not found!");
            }


        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }
        return response;
    }

    @Override
    public StatusAllResponse addUser(UserRequest userRequest) {

        StatusAllResponse response = new StatusAllResponse();

        try {
            String username = userRequest.getUsername();
            String password = passwordEncoder.encode(userRequest.getPassword());
            utility.checkUser(username,password);
            if(userRepository.findUserByUsernameAndActive(username,EnumAvailableStatus.ACTIVE.getValue()) != null){
              throw new MyException(ExceptionConstant.AVAILABLE_USERNAME,"Available username!");
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userRepository.save(user);
            response.setStatusResponse(StatusResponse.getSuccessMessage());
        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }

        return response;
    }

    @Override
    public StatusAllResponse updateUser(UserRequest userRequest) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            Long userId = userRequest.getUserId();
            String username = userRequest.getUsername();
            String password = userRequest.getPassword();
            utility.checkUser(username,password);
            Optional<User> optionalUser = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.getValue());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setUsername(username);
                user.setPassword(password);
                userRepository.save(user);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            } else {
                throw new MyException(ExceptionConstant.USER_NOT_FOUND,"User not found!");
            }


        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }
        return response;
    }

    @Override
    public StatusAllResponse deleteUser(Long userId) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            Optional<User> optionalUser = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.getValue());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                userRepository.save(user);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            } else {
                throw new MyException(ExceptionConstant.USER_NOT_FOUND,"User not found!");
            }

        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }
        return response;
    }
}
