package gc.garcol.nalsolution.payload.req;

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
public class RequestWork {

    private Long id;

    private String name;

    private Long startTime;

    private Long endTime;

    private WorkStatus status;

}
