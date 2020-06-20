package gc.garcol.nalsolution.entity;

import gc.garcol.nalsolution.annotation.AutoIncreaseID;
import gc.garcol.nalsolution.enums.WorkStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

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

    public Long getOwnerId() {
       return Objects.nonNull(account) ? account.getId() : null;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", accountID=" + getOwnerId() +
                '}';
    }
}
