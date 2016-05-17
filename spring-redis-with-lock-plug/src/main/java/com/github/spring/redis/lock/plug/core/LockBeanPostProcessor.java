package com.github.spring.redis.lock.plug.core;

import com.github.spring.redis.lock.api.Lock;
import com.github.spring.redis.lock.api.LockAware;
import com.github.spring.redis.lock.api.service.LockService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cenkakin
 */
public class LockBeanPostProcessor extends AbstractAdvisingBeanPostProcessor {

    private final LockService lockService;

    public LockBeanPostProcessor(LockService lockService) {
        Pointcut pointcut = new AnnotationMethodPointcut(Lock.class);
        this.advisor = new DefaultPointcutAdvisor(pointcut, new LockMethodInterceptor());
        this.lockService = lockService;
    }

    private final class AnnotationMethodPointcut extends StaticMethodMatcherPointcut {

        private final MethodMatcher methodResolver;

        AnnotationMethodPointcut(Class<? extends Annotation> annotationType) {
            this.methodResolver = new AnnotationMethodMatcher(annotationType);
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return this.methodResolver.matches(method, targetClass);
        }

    }

    private final class LockMethodInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            List<LockAware> keyList = resolveKeyList(invocation);
            Lock ann = AnnotationUtils.findAnnotation(invocation.getMethod(), Lock.class);
            lockService.lock(ann.keyActionPrefix(), keyList);
            return invocation.proceed();
        }

        private List<LockAware> resolveKeyList(MethodInvocation invocation) {
            if (invocation.getArguments().length == 0) {
                throw new IllegalArgumentException("Method with lock annotation has to have at least one argument.");
            }
            return Arrays.asList(invocation.getArguments())
                         .stream()
                         .filter(arg -> LockAware.class.isAssignableFrom(arg.getClass()))
                         .map(arg -> ((LockAware)arg))
                         .collect(Collectors.toList());
        }
    }

}
