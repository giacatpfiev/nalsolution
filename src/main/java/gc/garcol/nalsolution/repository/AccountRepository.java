package gc.garcol.nalsolution.repository;

import gc.garcol.nalsolution.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author thai-van
 **/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Update all fields except id and email.
     * Require run in a transaction.
     * @param account
     */
    @Modifying
    @Query( "UPDATE Account ac " +
            "SET ac.displayedName = :#{#account.displayedName}" +
            ", ac.avatarUrl = :#{#account.avatarUrl}" +
            ", ac.password = :#{#account.password} " +
            "WHERE ac.id = :#{#account.id}"
    )
    void update(@Param("account") Account account);

}

