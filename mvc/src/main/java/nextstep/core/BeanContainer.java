package nextstep.core;

import static nextstep.core.exception.BeanErrorMessage.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nextstep.core.exception.BeanErrorMessage;
import nextstep.core.exception.BeanException;

public class BeanContainer {

    private final Map<String, BeanDefinition> beanContainer;

    public BeanContainer() {
        this.beanContainer = new HashMap<>();
    }

    public void addBeans(BeanDefinition beanDefinition) {
        beanContainer.put(beanDefinition.getBeanName(), beanDefinition);
    }

    public void addBeans(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            beanContainer.put(beanDefinition.getBeanName(), beanDefinition);
        }
    }

    public List<Object> getBeansWithAnnotation(Class<? extends Annotation> annotation) {
        return beanContainer.values()
                .stream()
                .filter(beanDefinition -> beanDefinition.hasAnnotation(annotation))
                .map(BeanDefinition::getTarget)
                .collect(Collectors.toList());
    }

    public <T> T getBean(Class<T> type) {
        return beanContainer.values().stream()
                .filter(bean -> bean.isTypeOf(type))
                .map(bean -> (T) bean.getTarget())
                .findAny()
                .orElseThrow(() -> new BeanException(NOT_FOUND));
    }
}
