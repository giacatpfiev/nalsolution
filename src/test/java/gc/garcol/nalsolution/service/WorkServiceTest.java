package gc.garcol.nalsolution.service;

import gc.garcol.nalsolution._configuration.NalsolutionApplicationTest;
import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.WorkStatus;
import gc.garcol.nalsolution.enums.page.OrderBy;
import gc.garcol.nalsolution.exception.BadRequestException;
import gc.garcol.nalsolution.exception.NotFoundException;
import gc.garcol.nalsolution.repository.WorkRepository;
import gc.garcol.nalsolution.service.core.AccountService;
import gc.garcol.nalsolution.service.core.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author thai-van
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NalsolutionApplicationTest.class)
public class WorkServiceTest {

    @Autowired private WorkService workService;

    @Autowired private AccountService accountService;

    @Autowired private WorkRepository workRepository;

    private Account createAccount(String baseName) {

        Account account = Account.builder()
                .displayedName(baseName)
                .email(baseName)
                .avatarUrl(baseName)
                .password(baseName)
                .build();

        return accountService.create(account);
    }

    private Work buildRawWork(String baseName) {
        Work work = Work.builder()
                .endTime(new Timestamp(System.currentTimeMillis()))
                .startTime(new Timestamp(System.currentTimeMillis()))
                .name(baseName)
                .status(WorkStatus.PLANNING)
                .build();
        return work;
    }

    private Work createWork(String baseName) {
        log.info("WorkServiceTest -> {}. ========== INIT DATA ========", baseName);
        Account account = createAccount(baseName);
        Work work = buildRawWork(baseName);
        return workService.create(account.getId(), work);
    }

    private Work createWork(String baseName, Long accountId) {
        log.info("WorkServiceTest -> {}. ========== INIT DATA ========", baseName);
        Work work = buildRawWork(baseName);
        return workService.create(accountId, work);
    }

    @Test
    public void createWorkWithExistedAccount() {
        log.info("WorkServiceTest -> createWorkWithExistedAccount. ====== INIT DATA ======");
        Account account = createAccount("createWork");
        Long accountId = account.getId();

        log.info("WorkServiceTest -> createWorkWithExistedAccount. ====== TEST ======");
        Work work = buildRawWork("createWorkWithExistedAccount");
        workService.create(accountId, work);

        boolean existedAccount = workRepository.existsById(work.getId());
        Assert.assertTrue(existedAccount);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWorkWithNotExistAccount() {
        log.info("WorkServiceTest -> createWorkWithNotExistAccount. ====== TEST ======");
        Work work = buildRawWork("createWorkWithNotExistAccount");
        workService.create(-1L, work);

        boolean existedAccount = workRepository.existsById(work.getId());
        Assert.assertTrue(existedAccount);
    }

    @Test
    public void findById() {
        Work initWork = createWork("WorkServiceTest -> findById");
        Long id = initWork.getId();

        log.info("WorkServiceTest -> findById. ========== TEST ==========");
        Work work = workService.findById(id);
        log.info("WorkServiceTest -> findById. {}", work);
    }

    @Test(expected = NotFoundException.class)
    public void findNotExistedWork() {
        log.info("WorkServiceTest -> findNotExistedWork. ========== TEST ==========");
        Work work = workService.findById(-1L);
    }

    @Test
    public void updateWork() {
        String baseName = "WorkServiceTest -> updateWork";
        Work initWork = createWork(baseName);

        log.info(baseName, "======== TEST =======");
        String name = "new name";
        Timestamp startTime = new Timestamp(1_000_000L);
        Timestamp endTime = new Timestamp(2_000_000L);
        WorkStatus status = WorkStatus.COMPLETE;

        Work work = Work.builder()
                .id(initWork.getId())
                .endTime(endTime)
                .startTime(startTime)
                .name(name)
                .status(status)
                .build();

        int changed = workService.update(work);

        Work updatedWork = workService.findById(work.getId());

        Assert.assertEquals(1, changed);
        Assert.assertEquals(name, updatedWork.getName());
        Assert.assertEquals(startTime, updatedWork.getStartTime());
        Assert.assertEquals(endTime, updatedWork.getEndTime());
    }

    @Test
    public void updateNotExistWork() {

        String name = "new name";
        Timestamp startTime = new Timestamp(1_000_000L);
        Timestamp endTime = new Timestamp(2_000_000L);
        WorkStatus status = WorkStatus.COMPLETE;

        Work work = Work.builder()
                .id(-1L)
                .endTime(endTime)
                .startTime(startTime)
                .name(name)
                .status(status)
                .build();

        int changed = workService.update(work);

        Assert.assertEquals(0, changed);

    }

    @Test
    public void fetchPage() {
        String baseName = "WorkServiceTest -> fetchPage";
        Account account = createAccount(baseName);
        int numOfWork = 11;

        Long accountId = account.getId();
        IntStream.range(0, numOfWork).forEach(i -> {
            createWork(baseName + i, accountId);
        });

        int pageIndex = 1;
        int pageSize = 5;
        OrderBy orderBy = OrderBy.DES;
        String sortBy = "name";

        List<Work> works = workService.getSomeByUID(accountId, pageIndex, pageSize, sortBy, orderBy).toList();

        int size = works.size();
        for (int i = 0; i < size - 1; i++) {
            String preWorkName = works.get(i).getName();
            String afterWorkName = works.get(i + 1).getName();
            Assert.assertTrue(preWorkName.compareTo(afterWorkName) == 1);
        }

        log.info("[RESULT] {}: {}", baseName, works);
    }

    @Test(expected = BadRequestException.class)
    public void fetchPageWithWrongField() {
        int pageIndex = 1;
        int pageSize = 5;
        OrderBy orderBy = OrderBy.DES;
        String sortBy = "Name";

        workService.getSomeByUID(-1L, pageIndex, pageSize, sortBy, orderBy);
    }

    @Test
    public void deleteWork() {
        String baseName =  "WorkServiceTest -> deleteWork";
        Work work = createWork(baseName);

        int changed = workService.delete(work.getId());
        Assert.assertEquals(1, changed);
    }

}
