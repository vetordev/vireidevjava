package stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class StreamTest extends Object {

    public static void test() {
        List<String> names = Arrays.asList("Luke", "Leia", "Solo");
//        for (String name: names) System.out.println(name);
//
//        Iterator<String> iterator = names.iterator();
//
//        while(iterator.hasNext()) System.out.println(iterator.next());

        Stream<String> stream = names.stream();
        stream.forEach(System.out::println);

        Stream<String> parallelStream = names.parallelStream();
        parallelStream.forEach(System.out::println);

        names.parallelStream().map(String::toUpperCase).map(name -> name + "!").forEach(System.out::println);
    }
}
