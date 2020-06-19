package gc.garcol.nalsolution.mapper;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.WorkStatus;
import gc.garcol.nalsolution.payload.req.RequestWork;
import gc.garcol.nalsolution.payload.res.ResponseWork;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * @author thai-van
 **/
@Slf4j
public class WorkMapperTest {

    @Test
    public void mapFromReq() {
        RequestWork requestWork = new RequestWork();
        requestWork.setId(1L);
        requestWork.setName("mapFromReq");
        requestWork.setEndTime(System.currentTimeMillis());
        requestWork.setStartTime(System.currentTimeMillis() - 1_000_000);
        requestWork.setStatus(WorkStatus.PLANNING);

        Work work = WorkMapper.INSTANCE.map(requestWork);
        log.info("WorkMapperTest -> mapFromReq. req: {}, work: {}", requestWork, work);
    }

    @Test
    public void mapToRes() {
        Account account = Account.builder()
                .id(1L)
                .build();
        Work work = Work.builder()
                .id(1L)
                .status(WorkStatus.PLANNING)
                .name("name")
                .startTime(new Timestamp(System.currentTimeMillis()))
                .endTime(new Timestamp(System.currentTimeMillis()))
                .account(account)
                .build();

        ResponseWork responseWork = WorkMapper.INSTANCE.map(work);

        log.info("WorkMapperTest -> mapToRes. work: {}, res: {}", work, responseWork);

    }

}
