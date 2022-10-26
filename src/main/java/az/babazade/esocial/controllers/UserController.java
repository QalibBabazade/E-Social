package az.babazade.esocial.controllers;

import az.babazade.esocial.dto.requests.UserRequest;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.dto.responses.UserResponse;
import az.babazade.esocial.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Response<List<UserResponse>> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public StatusAllResponse addUser(@RequestBody UserRequest userRequest) {
        return userService.addUser(userRequest);
    }

    @GetMapping(value = "/{userId}")
    public Response<UserResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping()
    public StatusAllResponse updateUser(@RequestBody UserRequest userRequest)  {
        return userService.updateUser(userRequest);
    }

    @DeleteMapping(value = "/{userId}")
    public StatusAllResponse deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }
}
