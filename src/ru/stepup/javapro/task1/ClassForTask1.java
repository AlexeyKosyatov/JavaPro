package src.ru.stepup.javapro.task1;

import src.ru.stepup.javapro.task1.annotation.AfterSuite;
import src.ru.stepup.javapro.task1.annotation.AfterTest;
import src.ru.stepup.javapro.task1.annotation.BeforeSuite;
import src.ru.stepup.javapro.task1.annotation.BeforeTest;
import src.ru.stepup.javapro.task1.annotation.CsvSource;
import src.ru.stepup.javapro.task1.annotation.Test;

public class ClassForTask1 {
    @BeforeSuite
    public static void methodBeforeSuite() {
        System.out.println("выполнить MethodBeforeSuite");
    }

    @AfterSuite
    public static void methodAfterSuite() {
        System.out.println("выполнить MethodAfterSuite");
    }

    @CsvSource(value = "10, Java, 20, true")
    @Test(priority = 1)
    public void methodTest1(int a, String b, int c, boolean d) {
        System.out.println("выполнить MethodTest1");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
    }

    @Test
    public void methodTest5() {
        System.out.println("выполнить MethodTest5");
    }

    @Test(priority = 10)
    public void methodTest10() {
        System.out.println("выполнить MethodTest10");
    }

    @BeforeTest
    public void methodBeforeTest() {
        System.out.println("выполнить MethodBeforeTest");
    }

    @AfterTest
    public void methodAfterTest() {
        System.out.println("выполнить MethodAfterTest");
    }

}
