package gc.garcol.nalsolution.service.core.impl;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.page.OrderBy;
import gc.garcol.nalsolution.exception.NotFoundException;
import gc.garcol.nalsolution.manager.IDGeneratorManager;
import gc.garcol.nalsolution.repository.WorkRepository;
import gc.garcol.nalsolution.service.CommonRepositoryService;
import gc.garcol.nalsolution.service.core.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static gc.garcol.nalsolution.utils.PanigationUtil.PAGINATION;

/**
 * @author thai-van
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WorkServiceImpl implements WorkService {

    private final IDGeneratorManager idGeneratorManager;

    private final WorkRepository workRepository;

    private final CommonRepositoryService commonRepositoryService;

    @Override
    public Work create(Long uId, Work work) {

        Long id = idGeneratorManager.increaseAndGet(Work.class);
        work.setId(id);

        commonRepositoryService.runInSession(session -> {
            Account accountProxy = session.load(Account.class, uId);
            work.setAccount(accountProxy);
            session.persist(work);
        });

        log.info("WorkServiceImpl -> create. Message - uId: {}, new work: {}", uId, work);

        return work;
    }

    @Override
    public int update(Work work) {
        return workRepository.update(work);
    }

    @Override
    public Work findById(Long id) {
        Optional<Work> workOpt = workRepository.findById(id);
        return workOpt
                .orElseThrow(
                    () -> new NotFoundException("WorkServiceImpl -> findById. Message - id: " + id)
                );
    }

    @Override
    public List<Work> getSomeByUID(Long uId, int pageIndex, int pageSize, String sortBy, OrderBy orderBy) {

        PAGINATION.ensureExistedField(Work.class, sortBy);
        Pageable pageable = PAGINATION.of(pageIndex, pageSize, sortBy, orderBy);
        Page<Work> workPage = workRepository.getSomeByUID(uId, pageable);

        return workPage.toList();
    }

    @Override
    public List<Work> getAllByUID(Long uId) {
        return null;
    }

    @Override
    public int delete(Long id) {
        return workRepository.delete(id);
    }

}
