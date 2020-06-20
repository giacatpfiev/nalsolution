package gc.garcol.nalsolution.mapper;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.enums.WorkStatus;
import gc.garcol.nalsolution.payload.req.RequestWork;
import gc.garcol.nalsolution.payload.res.ResponseWork;
import gc.garcol.nalsolution.utils.codec.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void pageRes() {
        List<Work> works = Arrays.asList(
                Work.builder().id(1L).name("name1").build(),
                Work.builder().id(2L).name("name2").build(),
                Work.builder().id(3L).name("name3").build()
        );

        List<ResponseWork> res = WorkMapper.INSTANCE.map(works);

        Page<Work> page = new PageImpl(works);

        Page<ResponseWork> resPage = WorkMapper.INSTANCE.map(page);

        log.info("WorkMapperTest -> pageRes. {}", JsonUtil.JSON.toBeautyJson(resPage));

    }

}
