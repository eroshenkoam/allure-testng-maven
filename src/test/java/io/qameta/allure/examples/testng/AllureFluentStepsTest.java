package io.qameta.allure.examples.testng;

import org.testng.annotations.Test;

public class AllureFluentStepsTest {

    @Test
    public void testSomething() {
        AllureBaseSteps steps = new AllureBaseSteps();
        steps.doSomething()
                .doAnotherThing();
    }

    public static class AllureBaseSteps {

        @FluentStep
        public AllureBaseSteps doSomething() {
            return this;
        }

        @FluentStep
        public AllureBaseSteps doAnotherThing() {
            return this;
        }

    }

}
