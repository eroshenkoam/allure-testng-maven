package io.qameta.allure.examples.testng;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.StepResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;
import java.util.UUID;

import static io.qameta.allure.util.AspectUtils.getName;
import static io.qameta.allure.util.AspectUtils.getParameters;

@Aspect
public class FluentStepsAspects {

    private static final InheritableThreadLocal<AllureLifecycle> LIFECYCLE
            = new InheritableThreadLocal<AllureLifecycle>() {
        @Override
        protected AllureLifecycle initialValue() {
            return Allure.getLifecycle();
        }
    };

    @Pointcut("@annotation(io.qameta.allure.examples.testng.FluentStep)")
    public void withStepAnnotation() {
        //pointcut body, should be empty
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
        //pointcut body, should be empty
    }

    @Before("anyMethod() && withStepAnnotation()")
    public void stepStart(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final FluentStep step = methodSignature.getMethod().getAnnotation(FluentStep.class);

        final String uuid = UUID.randomUUID().toString();
        final String name = getName(step.value(), joinPoint);
        final List<Parameter> parameters = getParameters(methodSignature, joinPoint.getArgs());

        final StepResult result = new StepResult()
                .setName(name)
                .setParameters(parameters);

        getLifecycle().startStep(uuid, result);
        getLifecycle().stopStep();
    }

    /**
     * For tests only.
     *
     * @param allure allure lifecycle to set.
     */
    public static void setLifecycle(final AllureLifecycle allure) {
        LIFECYCLE.set(allure);
    }

    public static AllureLifecycle getLifecycle() {
        return LIFECYCLE.get();
    }
}
