package az.babazade.esocial.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Response<T>{

    @JsonProperty(value = "response")
    private T t;
    @JsonProperty(value = "status")
    private StatusResponse statusResponse;
}
