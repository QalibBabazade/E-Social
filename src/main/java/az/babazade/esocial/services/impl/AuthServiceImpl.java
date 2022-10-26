package az.babazade.esocial.services.impl;

import az.babazade.esocial.dto.requests.UserRequest;
import az.babazade.esocial.dto.responses.LoginResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusResponse;
import az.babazade.esocial.exception.ExceptionConstant;
import az.babazade.esocial.exception.MyException;
import az.babazade.esocial.security.JwtTokenProvider;
import az.babazade.esocial.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Response<LoginResponse> login(UserRequest userRequest) {
        Response<LoginResponse> response = new Response<>();
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwtToken = "Bearer " + jwtTokenProvider.generateJwtToken(auth);
            LoginResponse loginResponse = new LoginResponse(jwtToken);

            response.setT(loginResponse);
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
}
