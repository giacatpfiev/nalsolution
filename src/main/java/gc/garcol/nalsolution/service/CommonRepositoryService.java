package gc.garcol.nalsolution.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author thai-van
 **/
@Slf4j
@Service
public class CommonRepositoryService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public <R> R callInTransaction(Callable<R> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            log.error("CommonRepositoryService -> callInTransaction. Message: ", e);
            return null;
        }
    }

    @Transactional
    public void runInTransaction(Runnable runnable) {
        runnable.run();
    }

    @Transactional
    public <R> R callInSession(Function<Session, R> callable) {
        Session session = entityManager.unwrap(Session.class);
        return callable.apply(session);
    }

    @Transactional
    public void runInSession(Consumer<Session> runnable) {
        Session session = entityManager.unwrap(Session.class);
        runnable.accept(session);
    }

}
