package gc.garcol.nalsolution.mapper;

import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.payload.req.RequestWork;
import gc.garcol.nalsolution.payload.res.ResponseWork;

/**
 * @author thai-van
 **/
public interface WorkMapper {

    Work map(RequestWork requestWork);

    ResponseWork map(Work work);

}
