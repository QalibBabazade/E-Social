package az.babazade.esocial.dto.responses;


import az.babazade.esocial.entities.User;
import lombok.Data;

@Data
public class UserResponse {

    private Long userId;
    private String username;
    private String password;

    public UserResponse(User entity){
        this.userId = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
    }
}
