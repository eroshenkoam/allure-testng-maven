package io.qameta.allure.examples.testng;

import org.testng.annotations.Test;

public class AllureTagTest {

    @Test
    @Tag({"hello", "world"})
    public void testWithTag() {

    }

}
