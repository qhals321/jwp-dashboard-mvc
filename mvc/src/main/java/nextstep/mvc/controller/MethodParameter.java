package nextstep.mvc.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class MethodParameter {

    private final int parameterOrder;
    private final Annotation[] annotations;
    private final ParameterClass parameterClass;

    public MethodParameter(Class<?> parameterType, Parameter parameter,
                           int parameterOrder) {
        this.parameterOrder = parameterOrder;
        this.annotations = parameter.getDeclaredAnnotations();
        this.parameterClass = new ParameterClass(parameterType);
    }

    public boolean hasAnnotationType(Class<? extends Annotation> annotationType) {
        return Arrays.stream(annotations).anyMatch(annotationType::isInstance);
    }

    public Object createInstance(Map<String, String[]> requestParams) {
        return parameterClass.createInstance(requestParams);
    }

    public int getParameterOrder() {
        return parameterOrder;
    }

    public boolean isTypeOf(Class<?> aClass) {
        return parameterClass.isTypeOf(aClass);
    }

    public Object getAnnotationOf(Class<?> annotationClass) {
        return Arrays.stream(annotations).filter(annotation -> annotation.annotationType().isAssignableFrom(annotationClass))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("no exists parameter value"));
    }
}
