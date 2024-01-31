package src.ru.stepup.javapro.task2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {

        //1. Удаление из списка всех дубликатов
        var list1 = List.of(1, 1, 2, 2, 3, 3).stream().distinct().toList();
        System.out.println("1. Список без дубликатов: " + list1);

        // 2. Третье наибольшее число
        var i2 = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13)
                .sorted((o1, o2) -> o2 - o1)
                .skip(2)
                .limit(1)
                .findFirst()
                .orElseThrow();
        System.out.println("2. Третье наибольшее число: " + i2);

        // 3. Третье наибольшее уникальное число
        var i3 = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13)
                .distinct()
                .sorted((o1, o2) -> o2 - o1)
                .skip(2)
                .limit(1)
                .findFirst()
                .orElseThrow();
        System.out.println("3. Третье наибольшее уникальное число: " + i3);

        // 4. Три самых старших инженера
        enum Position {ENGINEER, DIRECTOR, MANAGER}
        class Employee {
            String name;
            int age;
            Position position;

            public Employee(String name, int age, Position position) {
                this.name = name;
                this.age = age;
                this.position = position;
            }
        }

        var oldestEngineerNames = Stream.of(
                        new Employee("Имя1", 23, Position.ENGINEER),
                        new Employee("Имя2", 24, Position.ENGINEER),
                        new Employee("Имя3", 25, Position.ENGINEER),
                        new Employee("Имя4", 26, Position.ENGINEER),
                        new Employee("Имя5", 27, Position.MANAGER))
                .filter(employee -> employee.position == Position.ENGINEER)
                .sorted((o1, o2) -> o2.age - o1.age)
                .limit(3)
                .map(e -> e.name)
                .toList();

        System.out.println("4. Три самых старших инженера: " + oldestEngineerNames);

        // 5. Средний возраст инженеров
        var avgAge = Stream.of(
                        new Employee("Имя1", 23, Position.ENGINEER),
                        new Employee("Имя2", 24, Position.ENGINEER),
                        new Employee("Имя3", 25, Position.ENGINEER),
                        new Employee("Имя4", 26, Position.ENGINEER),
                        new Employee("Имя5", 27, Position.MANAGER))
                .filter(e -> e.position == Position.ENGINEER)
                .mapToInt(e -> e.age)
                .average()
                .orElseThrow();
        System.out.println("5. Средний возраст инженеров: " + avgAge);

        // 6. Самое длинное слово
        var longestWord = List.of("q", "qwe", "qwer").stream()
                .sorted((o1, o2) -> o2.length() - o1.length())
                .limit(1)
                .findFirst()
                .orElseThrow();
        System.out.println("6. Самое длинное слово: " + longestWord);


        // 7. Подсчет количества слов
        var countWords = List.of("qq qq qq a zzz ss ss".split(" ")).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("7. Подсчет количества слов: " + countWords);

        // 8. сортировка слов по длине и по алфавиту
        System.out.println("8. сортировка слов по длине и по алфавиту:");
        List.of("aaa2", "aaa1", "bb2", "bb1", "c5", "c1")
                .stream()
                .sorted((o1, o2) -> {
                    if (o1.length() == o2.length()) return o1.compareTo(o2);
                    else return o1.length() - o2.length();
                })
                .forEach(System.out::println);

        // 9. Найти первое самое длинное слово в наборе строк из массива строк
        var str = List.of(
                        "123456789z qqqqq aaaaaa",
                        "zzzz aaaaaaaa fffffff",
                        "rrrrrrrr ttt 123456789z",
                        "tttt gggggg hhhhhh jjjjjjj llll",
                        "aaaa vvvv uuuuu"
                )
                .stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .sorted((o1, o2) -> o2.length() - o1.length())
                .findFirst()
                .orElseThrow();
        System.out.println("9. Первое самое длинное слово: " + str);

    }
}
