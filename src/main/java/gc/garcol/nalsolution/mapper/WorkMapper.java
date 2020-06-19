package gc.garcol.nalsolution.mapper;

import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.payload.req.RequestWork;
import gc.garcol.nalsolution.payload.res.ResponseWork;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

/**
 * @author thai-van
 **/
@Mapper
public interface WorkMapper {

    WorkMapper INSTANCE = Mappers.getMapper(WorkMapper.class);

    Work map(RequestWork requestWork);

    ResponseWork map(Work work);

    default Long toMillis(Timestamp timestamp) {
        return timestamp.getTime();
    }

    default Timestamp fromMillis(Long millis) {
        return new Timestamp(millis);
    }

}
