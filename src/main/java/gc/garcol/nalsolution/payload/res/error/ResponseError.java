package gc.garcol.nalsolution.payload.res.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author thai-van
 **/
@Builder
@AllArgsConstructor
@Data
@JsonPropertyOrder({"timestamp", "status", "message", "path"})
public class ResponseError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime localDateTime;

    private int status;

    private String message;

    private String path;

}
