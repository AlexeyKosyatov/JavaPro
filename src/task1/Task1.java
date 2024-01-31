package src.task1;

import java.lang.reflect.InvocationTargetException;

public class Task1 {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException {

        Class<ClassForTask1> cls = ClassForTask1.class;

        TestRunner.runTests(cls);

    }
}
