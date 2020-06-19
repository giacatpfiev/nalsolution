package gc.garcol.nalsolution.repository;

import gc.garcol.nalsolution.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author thai-van
 **/
@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Query(
            value = "SELECT w FROM Work w WHERE w.account.id = ?1",
            countQuery = "SELECT count(w.account.id) FROM Work w WHERE w.account.id = ?1"
    )
    Page<Work> getSomeByUID(Long accountId, Pageable pageable);

}
