package ru.stoliarenko.gb.lesson7;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.eclipse.jdt.annotation.*;

import ru.stoliarenko.gb.lesson7.annotations.*;
import ru.stoliarenko.gb.lesson7.model.PrioritizedMethod;

/**
 * Homework java3 lesson7
 * 
 * Создать класс, который может выполнять «тесты». 
 * В качестве тестов выступают классы с наборами методов с аннотациями @Test. 
 * Для этого у него должен быть статический метод start(), 
 * которому в качестве параметра передается или объект типа Class, или имя класса. 
 * Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, 
 * если такой имеется. Далее запущены методы с аннотациями @Test, 
 * а по завершении всех тестов – метод с аннотацией @AfterSuite. 
 * К каждому тесту необходимо добавить приоритеты (int числа от 1 до 10), 
 * в соответствии с которыми будет выбираться порядок их выполнения. 
 * Если приоритет одинаковый, то порядок не имеет значения. 
 * Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре, 
 * иначе необходимо бросить RuntimeException при запуске «тестирования».
 * 
 * @author Stoliarenko Alexander
 */
public final class TestsRunner {

    public static void main(String[] args) {
        if (args.length == 0) args = new String[] {"ru.stoliarenko.gb.lesson7.test_classes.TestClassValid",
                                      "ru.stoliarenko.gb.lesson7.test_classes.TestClassValidFailedAssertion",
                                      "ru.stoliarenko.gb.lesson7.test_classes.TestClassValidFailedException",
                                      "ru.stoliarenko.gb.lesson7.test_classes.TestClassValidNoTests",
                                      "ru.stoliarenko.gb.lesson7.test_classes.TestClassInvalidThreeAfter"};
        start(args);
    }
    
    /**
     * Принимает массив строк с именами тестируемых классов
     * и передает все не null строки методу {@link proceedTestClass}
     * В случае если класс с указанным именем не существует
     * сообщение об этом выводится на экран.
     * 
     * @param args массив строк с именами классов
     */
    public static void start(@Nullable final String[] args) {
        if (args == null || args.length == 0) throw new RuntimeException("no arguments");
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
    
    /**
     * Создает очередь исполнения методов и список возникших проблем при исполнении
     * тестов из класса, имя которого передано в параметрах
     * 
     * Заполнение очереди методов делегирует методу {@link putMethodsInQueue}
     * Выполнение методов и занесение проблем в список делегирует методу {@link executeTests}
     * Вывод результатов делегирует методу {@link printExceptions}
     * 
     * @param className мя класса для обработки
     * @throws ClassNotFoundException если класса с указанным именем не существует
     */
    private static void proceedTestClass(@NonNull final String className) throws ClassNotFoundException {
        final Queue<PrioritizedMethod> que = new PriorityQueue<>();
        final List<String> exceptions = new LinkedList<>();
        
        System.out.println(String.format("Testing class %s...", className));
        putMethodsInQueue(que, className);
        executeTests(que, exceptions);
        printExceptions(exceptions, className);
        System.out.println("... finished.\n");
    }
    
    /**
     * Форматирует результат прохождения тестов классом и выводит его на экран
     * 
     * @param exceptions возникшие проблемы при прохождении тестов
     * @param className имя тестируемого класса
     */
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
    
    /**
     * Выполняет методы из переданной в параметрах очереди
     * и заносит возникшие проблемы в переданный в параметрах список.
     * 
     * @param que - очередь методов для выполнения
     * @param exceptions - список возникших проблем
     */
    private static void executeTests(@NonNull final Queue<PrioritizedMethod> que, @NonNull final List<String> exceptions) {
        PrioritizedMethod method;
        while ((method = que.poll()) != null) {
            try {
                method.invoke();
            } catch(Exception e) {
                exceptions.add(String.format("+Exception in method %s, message: %s", method.toString(), e.getCause().getMessage()));
            }
        }
    }
    
    /**
     * Извлекает из класса, имя которого передано в параметрах методы,
     * методы, помеченые аннотациями {@link Test, @link BeforeSuite, @link AfterSuite}}
     * оборачивает в {@link PrioritizedMethod} с указанием приоритета в соответствии
     * с их аннотациями и заносит в переданную в параметрах очередь.
     * Если ни один метод не помечен (@link Test) то очередь очищается.  
     * 
     * @param que - очередь, в которую будут заноситься методы
     * @param className - имя класса для обработки
     * @throws ClassNotFoundException - если класса с данным именем не существует
     */
    @SuppressWarnings("unused")
    private static void putMethodsInQueue(@NonNull final Queue<PrioritizedMethod> que, @NonNull final String className) throws ClassNotFoundException {
        final Class<?> testsClass = Class.forName(className);
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
