package gc.garcol.nalsolution.payload.req;

import gc.garcol.nalsolution.enums.WorkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author thai-van
 **/
@Getter
@Setter
@NoArgsConstructor
public class RequestWork {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;

    @NotNull
    private WorkStatus status;

    @Override
    public String toString() {
        return "RequestWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
