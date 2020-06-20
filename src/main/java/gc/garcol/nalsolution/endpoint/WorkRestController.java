package gc.garcol.nalsolution.endpoint;

import gc.garcol.nalsolution.authentication.SimpleUserDetail;
import gc.garcol.nalsolution.entity.Work;
import gc.garcol.nalsolution.exception.BadRequestException;
import gc.garcol.nalsolution.mapper.WorkMapper;
import gc.garcol.nalsolution.payload.req.RequestWork;
import gc.garcol.nalsolution.payload.req.RequestWorkPage;
import gc.garcol.nalsolution.payload.res.ResponseWork;
import gc.garcol.nalsolution.service.core.WorkService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author thai-van
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work")
public class WorkRestController {

    private final WorkService workService;

    private final WorkMapper mapper = WorkMapper.INSTANCE;

    @PostMapping("/some")
    public ResponseEntity<Page<ResponseWork>> fetchSomeWorks(@AuthenticationPrincipal SimpleUserDetail currentUser,
                                                             @Valid @RequestBody RequestWorkPage req) {

        log.info("WorkRestController -> fetchSomeWorks. Message - uId: {}, req: {}", currentUser.getId(), req);

        Page<Work> workPage = workService.getSomeByUID(
                currentUser.getId(),
                req.getPageIndex(),
                req.getPageSize(),
                req.getSortBy(),
                req.getOrderBy()
        );

        Page<ResponseWork> res = mapper.map(workPage);

        return ResponseEntity.ok(res);

    }

    @PostMapping
    public ResponseEntity<ResponseWork> createWork(@AuthenticationPrincipal SimpleUserDetail currentUser,
                                                   @Valid @RequestBody RequestWork req) {
        log.info("WorkRestController -> createWork. Message - uId: {}, req: {}", currentUser.getId(), req);

        Work reqWork = mapper.map(req);
        Work work = workService.create(currentUser.getId(), reqWork);
        ResponseWork res = mapper.map(work);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{workId}")
    public ResponseEntity<ResponseWork> getOne(@AuthenticationPrincipal SimpleUserDetail currentUser,
                                               @PathVariable("workId") Long workId) {

        log.info("WorkRestController -> createWork. Message - uId: {}, req: {}", currentUser.getId(), workId);

        Work work = workService.findByIdAndAccountId(workId, currentUser.getId());
        ResponseWork res = mapper.map(work);

        return ResponseEntity.ok(res);

    }

    @PutMapping
    public ResponseEntity update(@AuthenticationPrincipal SimpleUserDetail currentUser,
                                 @Valid @RequestBody RequestWork req) {

        log.info("WorkRestController -> createWork. Message - uId: {}, req: {}", currentUser.getId(), req);

        Assert.notNull(req.getId());
        Assert.isTrue(req.getEndTime().compareTo(req.getStartTime()) >= 0, "startTime > endTime");

        Work work = mapper.map(req);
        workService.update(currentUser.getId(), work);

        return ResponseEntity.ok().build();

    }

}
