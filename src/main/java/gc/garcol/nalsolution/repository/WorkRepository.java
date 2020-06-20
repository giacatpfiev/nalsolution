package gc.garcol.nalsolution.repository;

import gc.garcol.nalsolution.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    /**
     * Update all fields except {@link Work#account} and {@link Work#id}
     *
     * @param work
     * @return
     */
    @Modifying
    @Query(
            value = "UPDATE Work w " +
                    "SET w.name = :#{#work.name}" +
                    ", w.startTime = :#{#work.startTime}" +
                    ", w.endTime = :#{#work.endTime} " +
                    "WHERE w.id = :#{#work.id} " +
                    "AND w.account.id = :accountId"
    )
    int update(@Param("accountId") Long accountId, @Param("work") Work work);

    /**
     *
     * @param id
     * @param accountId
     * @return
     */
    Optional<Work> findByIdAndAccountId(Long id, Long accountId);

    /**
     * Delete by work id and ownerId
     *
     * @param uId
     * @param id
     * @return number of changed data.
     */
    @Modifying
    @Query("DELETE FROM Work w WHERE w.account.id = ?1 AND w.id = ?2")
    int delete(Long uId, Long id);

}
