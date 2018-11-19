package ru.stoliarenko.gb.lesson7;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.eclipse.jdt.annotation.*;

import ru.stoliarenko.gb.lesson7.annotations.*;

public final class TestsRunner {

    public static void main(@NonNull final String[] args) {
        run(args);
    }

    public static void run(@NonNull final String[] args) {
        for(int i = 0; i < args.length; i++) {
            final String className = args[i];
            if (className == null) continue;
            try {
                proceedTestClass(className);
            } catch (final ClassNotFoundException e) {
                System.out.println(String.format("Class %s not found!", className));
            }
        }
    }
    
    private static void proceedTestClass(@NonNull final String className) throws ClassNotFoundException {
        final Queue<PrioritizedMethod> que = new PriorityQueue<>();
        final List<String> exceptions = new LinkedList<>();
        
        putMethodsInQueue(que, className);
        executeTests(que, exceptions);
        printExceptions(exceptions, className);
    }
    
    private static void printExceptions(@NonNull final List<String> exceptions, @NonNull final String className) {
        if (exceptions.isEmpty()) {
            System.out.println(String.format("All tests from %s ended with success", className));
            return;
        }
        System.out.println(String.format("Tests failed at %s", className));
        for (String string : exceptions) {
            System.out.println(string);
        }
    }

    private static void executeTests(@NonNull final Queue<PrioritizedMethod> que, @NonNull final List<String> exceptions) {
        PrioritizedMethod method;
        while ((method = que.poll()) != null) {
            try {
                method.invoke();
            } catch(Exception e) {
                exceptions.add(String.format("+Exception in method %s, message: %s", method.toString(), e.getMessage()));
            }
        }
    }

    @SuppressWarnings("unused")
    private static void putMethodsInQueue(@NonNull final Queue<PrioritizedMethod> que, @NonNull final String className) throws ClassNotFoundException {
        final Class testsClass = Class.forName(className);
        final Method[] methods = testsClass.getDeclaredMethods();
        
        boolean hasBeforeSuite = false;
        boolean hasAfterSuite = false;
        boolean hasTests = false;
        
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getAnnotation(BeforeSuite.class) != null) {
                if (hasBeforeSuite) throw new RuntimeException("Class can not have more than 1 @BeforeSuite");
                if (methods[i].getAnnotation(AfterSuite.class) != null) throw new RuntimeException("Method can not have both BeforeSuite and AfterSuite");
                final PrioritizedMethod pMethod = new PrioritizedMethod(methods[i], 0);
                pMethod.setFirst(); //sets special priority value
                que.add(pMethod);
                hasBeforeSuite = true;
                continue;
            }
            if (methods[i].getAnnotation(AfterSuite.class) != null) {
                if (hasAfterSuite) throw new RuntimeException("Class can not have more than 1 @AfterSuite");
                if (methods[i].getAnnotation(BeforeSuite.class) != null) throw new RuntimeException("Method can not have both BeforeSuite and AfterSuite");
                final PrioritizedMethod pMethod = new PrioritizedMethod(methods[i], 0);
                pMethod.setLast(); //sets special priority value
                que.add(pMethod);
                hasAfterSuite = true;
                continue;
            }
            if (methods[i].getAnnotation(Test.class) != null) {
                final int priority = methods[i].getAnnotation(Test.class).priority();
                que.add(new PrioritizedMethod(methods[i], priority));
                if (!hasTests) hasTests = true;
            }
        }
        
        if (!hasTests) que.clear();
    }

}
