package gc.garcol.nalsolution.configuration;

import gc.garcol.nalsolution.annotation.AutoIncreaseID;
import gc.garcol.nalsolution.manager.IDGeneratorManager;
import gc.garcol.nalsolution.service.IDGeneratorService;
import gc.garcol.nalsolution.utils.ClassLoaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author thai-van
 **/
@Slf4j
@Configuration
@PropertySource("classpath:IDGenerator.properties")
public class IDGeneratorConfiguration {

    @Value("${scan-base-package}")
    private String scanBasePackage;

    @Bean
    public IDGeneratorManager buildIDGeneratorManager(IDGeneratorService idGeneratorService) {
        List<Class<?>> classes = ClassLoaderUtil.getAllClassInPackage(scanBasePackage);

        IDGeneratorManager idGeneratorManager = new IDGeneratorManager();
        Method registerMethod = idGeneratorManager.getRegisterMethod();
        registerMethod.setAccessible(true);

        for (Class<?> clazz : classes) {
            AutoIncreaseID classAnnotation = clazz.getDeclaredAnnotation(AutoIncreaseID.class);

            if (Objects.isNull(classAnnotation)) continue;

            try {
                Long maxId = idGeneratorService.getMaxId(clazz);
                registerMethod.invoke(idGeneratorManager, clazz, maxId);

            } catch (Exception e) {
                log.error("IDGeneratorConfiguration -> buildIDGeneratorManager. ", e);
            }
        }

        return idGeneratorManager;
    }

}
