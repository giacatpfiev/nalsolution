package gc.garcol.nalsolution.service.core;

import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.page.OrderBy;
import org.springframework.data.domain.Page;

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

    int update(Long uId, Work work);

    Work findByIdAndAccountId(Long id, Long accountId);

    Work findById(Long id);

    Page<Work> getSomeByUID(Long uId, int pageIndex, int pageSize, String sortBy, OrderBy orderBy);

    List<Work> getAllByUID(Long uId);

    int delete(Long uId, Long id);

}
