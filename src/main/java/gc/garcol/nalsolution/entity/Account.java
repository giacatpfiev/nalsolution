package gc.garcol.nalsolution.entity;

import gc.garcol.nalsolution.annotation.AutoIncreaseID;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

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
public class Account {

    @Id
    private Long id;

    @NaturalId
    private String email;

    private String password;

    private String displayedName;

    private String avatarUrl;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Work> works;

}
