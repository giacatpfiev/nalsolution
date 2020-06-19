package gc.garcol.nalsolution.payload.res;

import gc.garcol.nalsolution.enums.WorkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thai-van
 **/
@Getter
@Setter
@NoArgsConstructor
public class ResponseWork {

    private Long id;

    private String name;

    private Long startTime;

    private Long endTime;

    private WorkStatus status;

    @Override
    public String toString() {
        return "ResponseWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
