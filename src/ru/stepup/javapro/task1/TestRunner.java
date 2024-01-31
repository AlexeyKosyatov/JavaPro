package src.ru.stepup.javapro.task1;

import src.ru.stepup.javapro.task1.annotation.AfterSuite;
import src.ru.stepup.javapro.task1.annotation.AfterTest;
import src.ru.stepup.javapro.task1.annotation.BeforeSuite;
import src.ru.stepup.javapro.task1.annotation.BeforeTest;
import src.ru.stepup.javapro.task1.annotation.CsvSource;
import src.ru.stepup.javapro.task1.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void runTests(Class cls) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        System.out.println("Начало");

        Class<?> c = cls;
        Object objectForTask1 = c.newInstance();

        Method methodBeforeSuite = null;
        Method methodAfterSuite = null;
        List<Method> methodsTest = new ArrayList<>();
        List<Method> methodsBeforeTest = new ArrayList<>();
        List<Method> methodsAfterTest = new ArrayList<>();

        Method[] methods = cls.getDeclaredMethods();

        for (Method m : methods) {

            if (m.isAnnotationPresent(BeforeSuite.class)) {
                if (methodBeforeSuite == null) {
                    methodBeforeSuite = m;
                } else {
                    throw new RuntimeException("Аннотацию @BeforeSuite можно применить только один раз");
                }

                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new RuntimeException("Аннотацию @BeforeSuite можно применить только для статического метода");
                }
            }

            if (m.isAnnotationPresent(AfterSuite.class)) {
                if (methodAfterSuite == null) {
                    methodAfterSuite = m;
                } else {
                    throw new RuntimeException("Аннотацию @AfterSuite можно применить только один раз");
                }


                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new RuntimeException("Аннотацию @AfterSuite можно применить только для статического метода");
                }
            }

            if (m.isAnnotationPresent(Test.class)) {

                if (m.getAnnotation(Test.class).priority() < 1 || m.getAnnotation(Test.class).priority() > 10) {
                    throw new RuntimeException("В аннотации @Test значение priority д.б. в пределах от 1 до 10 включительно");
                }

                methodsTest.add(m);
            }

            if (m.isAnnotationPresent(BeforeTest.class)) {
                methodsBeforeTest.add(m);
            }

            if (m.isAnnotationPresent(AfterTest.class)) {
                methodsAfterTest.add(m);
            }

        }

        methodsTest.sort((method1, method2)
                -> method2.getAnnotation(Test.class).priority() - method1.getAnnotation(Test.class).priority());


        System.out.println("");

        if (methodBeforeSuite != null) {
            methodBeforeSuite.invoke(objectForTask1);
        }

        System.out.println("");

        for (Method m : methodsTest) {

            for (Method mBefore : methodsBeforeTest) {
                mBefore.invoke(objectForTask1);
            }

            invokeTestMethod(objectForTask1, m);

            for (Method mAfter : methodsAfterTest) {
                mAfter.invoke(objectForTask1);
            }

            System.out.println("");
        }

        if (methodAfterSuite != null) {
            methodAfterSuite.invoke(objectForTask1);
        }

        System.out.println("");

        System.out.println("Конец");
    }


    private static void invokeTestMethod(Object o, Method m) throws InvocationTargetException, IllegalAccessException {
        if (m.isAnnotationPresent(CsvSource.class)) {

            String value = m.getAnnotation(CsvSource.class).value();
            System.out.println("value = " + value);
            String[] params = value.split(", ");
            var paramTypes = m.getParameterTypes();

            if (m.getParameterCount() != params.length) {
                throw new RuntimeException("Количество параметров в методе " + m.getName() + " не совпадает c переданными");
            }

            Object[] arguments = new Object[params.length];

            for (int i = 0; i < params.length; i++) {
                if (paramTypes[i].equals(int.class)) {
                    arguments[i] = Integer.parseInt(params[i]);
                } else if (paramTypes[i].equals(String.class)) {
                    arguments[i] = params[i];
                } else if (paramTypes[i].equals(boolean.class)) {
                    arguments[i] = Boolean.parseBoolean(params[i]);
                }
            }

            m.invoke(o, arguments);

        } else {
            m.invoke(o);
        }
    }
}
