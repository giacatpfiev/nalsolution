package gc.garcol.nalsolution.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author thai-van
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IDGeneratorService {

    private final CommonRepositoryService commonRepositoryService;

    public Long getMaxId(Class<?> clazz) {

        log.info("IDGeneratorService -> getMaxId. Class: {}, field: {}.", clazz);

        String query = String.format("SELECT MAX(c.id) FROM %s c", clazz.getName());

        Long maxId =
                commonRepositoryService.callInSession(
                        (session) -> session.createQuery(query, Long.class).getSingleResult());

        return Objects.nonNull(maxId) ? maxId : 0L;

    }
}
