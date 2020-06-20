package gc.garcol.nalsolution.mapper;

import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.payload.req.RequestWork;
import gc.garcol.nalsolution.payload.res.ResponseWork;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author thai-van
 **/
@Mapper
public interface WorkMapper {

    WorkMapper INSTANCE = Mappers.getMapper(WorkMapper.class);

    Work map(RequestWork requestWork);

    ResponseWork map(Work work);

    List<ResponseWork> map(List<Work> works);

    default Page<ResponseWork> map(Page<Work> works) {

        List<ResponseWork> res = map(works.toList());
        return new PageImpl<>(res, works.getPageable(), works.getTotalElements());

    }

    default Long toMillis(Timestamp timestamp) {
        return Objects.nonNull(timestamp) ? timestamp.getTime() : 0L;
    }

    default Timestamp fromMillis(Long millis) {
        return new Timestamp(millis);
    }

}
