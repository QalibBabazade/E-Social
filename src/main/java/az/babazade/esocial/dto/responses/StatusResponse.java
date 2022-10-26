package az.babazade.esocial.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponse {

    private Integer code;
    private String message;

    public static final Integer STATUS_CODE = 1;
    public static final String STATUS_MESSAGE = "SUCCESS";

    public static StatusResponse getSuccessMessage(){
        return new StatusResponse(STATUS_CODE,STATUS_MESSAGE);
    }
}
