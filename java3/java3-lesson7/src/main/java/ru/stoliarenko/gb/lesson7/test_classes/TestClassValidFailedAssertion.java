package ru.stoliarenko.gb.lesson7.test_classes;

import ru.stoliarenko.gb.lesson7.annotations.*;
import ru.stoliarenko.gb.lesson7.model.Assert;

public final class TestClassValidFailedAssertion {
    
    @BeforeSuite
    public void beforeTests() {
        System.out.println("Before tests");
    }
    
    public void someNonTestMethod() {
        System.out.println("Non-test method");
    }
    
    @Test(priority = 1)
    public void test1() {
        System.out.println("Test#1 (priority 1)");
    }
    @Test(priority = 3)
    public void test2() {
        System.out.println("Test#2 (priority 3)");
        Assert.assertTrue(false);
    }
    @Test()
    public void test3() {
        System.out.println("Test#3 (priority default(5))");
    }
    @Test(priority = 7)
    public void test4() {
        System.out.println("Test#4 (priority 7)");
    }
    @Test(priority = 7)
    public void test5() {
        System.out.println("Test#5 (priority 7)");
    }
    
    @AfterSuite
    public void afterTests() {
        System.out.println("After tests");
    }

}
