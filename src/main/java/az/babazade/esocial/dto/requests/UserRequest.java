package az.babazade.esocial.dto.requests;

import lombok.Data;

@Data
public class UserRequest {

    private Long userId;
    private String username;
    private String password;
}
