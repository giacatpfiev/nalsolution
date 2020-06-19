package gc.garcol.nalsolution.service.core;

import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.page.OrderBy;

import java.util.List;

/**
 * @author thai-van
 **/
public interface WorkService {

    /**
     *
     * @param uId
     * @param work
     * @return
     */
    Work create(Long uId, Work work);

    int update(Work work);

    Work findById(Long id);

    List<Work> getSomeByUID(Long uId, int pageIndex, int pageSize, String sortBy, OrderBy orderBy);

    List<Work> getAllByUID(Long uId);

    int delete(Long id);

}
