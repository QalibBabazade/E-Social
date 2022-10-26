package az.babazade.esocial.services;

import az.babazade.esocial.dto.requests.UserRequest;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.dto.responses.UserResponse;

import java.util.List;

public interface UserService {

    Response<List<UserResponse>> getAllUsers();

    Response<UserResponse> getUserById(Long userId);

    StatusAllResponse addUser(UserRequest userRequest);

    StatusAllResponse updateUser(UserRequest userRequest);

    StatusAllResponse deleteUser(Long userId);
}
