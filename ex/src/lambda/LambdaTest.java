package lambda;

import java.util.Arrays;
import java.util.List;


public class LambdaTest {
    public static void test() {
        Calc add = new Add();
        System.out.println(add.exec(1, 3));

        Calc multiply = new Multiply();
        System.out.println(multiply.exec(1, 3));

        Calc subtract = (double a, double b) -> {
            return a - b;
        };
        System.out.println(subtract.exec(1, 3));

        Calc divide = (a, b) -> a / b;
        System.out.println(divide.exec(1, 3));

        foreach();
    }

    public static void foreach() {
        List<String> names = Arrays.asList("Luke", "Leia");

        names.forEach((name) -> {
            System.out.println(name + ".");
        });

        names.forEach(System.out::println);
    }
}
