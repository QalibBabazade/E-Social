package az.babazade.esocial.services;

import az.babazade.esocial.dto.requests.UserRequest;
import az.babazade.esocial.dto.responses.LoginResponse;
import az.babazade.esocial.dto.responses.Response;

public interface AuthService {

    Response<LoginResponse> login(UserRequest userRequest);
}
