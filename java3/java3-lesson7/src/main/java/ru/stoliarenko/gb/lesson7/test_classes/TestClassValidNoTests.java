package ru.stoliarenko.gb.lesson7.test_classes;

import ru.stoliarenko.gb.lesson7.annotations.*;

public final class TestClassValidNoTests {
    
    @BeforeSuite
    public void beforeTests() {
        System.out.println("Before tests");
    }
    
    public void someNonTestMethod() {
        System.out.println("Non-test method");
    }
    
    @AfterSuite
    public void afterTests() {
        System.out.println("After tests");
    }

}
