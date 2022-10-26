package az.babazade.esocial.controllers;

import az.babazade.esocial.dto.requests.UserRequest;
import az.babazade.esocial.dto.responses.LoginResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private AuthService authService;



    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    @PostMapping(value = "/login")
    public Response<LoginResponse> login(@RequestBody UserRequest userRequest){
        return authService.login(userRequest);
    }

}
