package gc.garcol.nalsolution.service.core;

import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.page.OrderBy;

import java.util.List;
import java.util.Set;

/**
 * @author thai-van
 **/
public interface WorkService {

    /**
     *
     * @param work
     * @return
     */
    Work create(Work work);

    Work save(Work work);

    Work findById(Long id);

    List<Work> getSomeByUID(Long uId, String sortBy, OrderBy orderBy);

    List<Work> getAllByUID(Long uId);

    boolean delete(Long id);

    boolean deleteAll(Set<Long> ids);

}
