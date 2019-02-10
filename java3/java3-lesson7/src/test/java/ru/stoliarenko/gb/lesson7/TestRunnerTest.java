package ru.stoliarenko.gb.lesson7;

import org.junit.Test;

public final class TestRunnerTest {
    
    @Test(expected = RuntimeException.class)
    public void nullTest() {
        TestsRunner.start(null);
    }
    @Test(expected = RuntimeException.class)
    public void emptyArgumentsTest() {
        TestsRunner.start(new String[0]);
    }
    @Test(expected = RuntimeException.class)
    public void moreThanOneAfterTest() {
        TestsRunner.start(new String[]{"ru.stoliarenko.gb.lesson7.test_classes.TestClassInvalidThreeAfter"});
    }
    @Test(expected = RuntimeException.class)
    public void moreThanOneBeforeTest() {
        TestsRunner.start(new String[]{"ru.stoliarenko.gb.lesson7.test_classes.TestClassInvalidTwoBefore"});
    }
    @Test
    public void validClassesTest() {
        final String[] classes = new String[] {"ru.stoliarenko.gb.lesson7.test_classes.TestClassValid",
                "ru.stoliarenko.gb.lesson7.test_classes.TestClassValidFailedAssertion",
                "ru.stoliarenko.gb.lesson7.test_classes.TestClassValidFailedException",
                "ru.stoliarenko.gb.lesson7.test_classes.TestClassValidNoTests"};
        TestsRunner.start(classes);
    }
    
}
