package gc.garcol.nalsolution.service.core.impl;

import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.page.OrderBy;
import gc.garcol.nalsolution.service.core.WorkService;

import java.util.List;
import java.util.Set;

/**
 * @author thai-van
 **/
public class WorkServiceImpl implements WorkService {

    @Override
    public Work create(Work work) {
        return null;
    }

    @Override
    public Work save(Work work) {
        return null;
    }

    @Override
    public Work findById(Long id) {
        return null;
    }

    @Override
    public List<Work> getSomeByUID(Long uId, String sortBy, OrderBy orderBy) {
        return null;
    }

    @Override
    public List<Work> getAllByUID(Long uId) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean deleteAll(Set<Long> ids) {
        return false;
    }

}
