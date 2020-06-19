package gc.garcol.nalsolution.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author thai-van
 **/
@Slf4j
public class ClassLoaderUtil {

    static final ClassPathScanningCandidateComponentProvider provider =
        new ClassPathScanningCandidateComponentProvider(false);

    static {
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
    }

    public static List<Class<?>> getAllClassInPackage(String packagePath) {

        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(packagePath);

        List<Class<?>> classes = new ArrayList<>(beanDefinitions.size());

        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                classes.add(clazz);
            } catch (ClassNotFoundException e) {
                log.error("ClassLoaderUtil -> getAllClassInPackage. ", e);
            }
        }

        return classes;
    }
}
