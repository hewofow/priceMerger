import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class App {
    public static void main(String[] args) {

        Set<Price> set1 = Set.of(
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 2),
                        LocalDate.of(2022, 1, 13), 10000),
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 13),
                        LocalDate.of(2022, 1, 20), 5000),
                new Price("lapsha", 1, 2,
                        LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 1, 10), 20000)
        );

        Set<Price> set2 = Set.of(
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 5),
                        LocalDate.of(2022, 1, 16), 3000),
                new Price("bublik", 2, 2,
                        LocalDate.of(2022, 1, 5),
                        LocalDate.of(2022, 1, 16), 555),
                new Price("lapsha", 1, 2,
                        LocalDate.of(2022, 1, 3),
                        LocalDate.of(2022, 1, 23), 18000),
                new Price("pomidor", 1, 2,
                        LocalDate.of(2022, 1, 3),
                        LocalDate.of(2022, 1, 23), 666)
        );

        Collection<Price> set3 = PriceService.mergePrices(set1, set2);
        SortedSet<Price> sortedSet = new TreeSet<>(set3);

        for (Price price: sortedSet) {
            System.out.println(price);
        }

        System.out.println("");


        Map<Price, Set<Price>> testMap = PriceService.setsToMap(set1, set2);

        for (Map.Entry<Price, Set<Price>> entry : testMap.entrySet()) {
            System.out.println("key: " + entry.getKey().toString());
            entry.getValue().forEach(System.out::println);
            System.out.println("-----------------------------\n");
        }

    }
}
