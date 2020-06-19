package gc.garcol.nalsolution.entity;

import gc.garcol.nalsolution.annotation.AutoIncreaseID;
import gc.garcol.nalsolution.enums.WorkStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author thai-van
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@AutoIncreaseID
public class Work {

    @Id
    private Long id;

    private String name;

    private Timestamp startTime;

    private Timestamp endTime;

    @Enumerated(EnumType.STRING)
    private WorkStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

}
