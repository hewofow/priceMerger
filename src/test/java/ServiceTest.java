import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ServiceTest {
    static Set<Price> set1;
    static Set<Price> set2;
    static Set<Price> setMerged;

    static Map<Price, Set<Price>> map;
    static Set<Price> setBublikOne;
    static Set<Price> setBublikTwo;
    static Set<Price> setLapsha;
    static Set<Price> setPomidor;

    @BeforeClass
    public static void fillCollections() {

        set1 = Set.of(
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

        set2 = Set.of(
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

        setMerged = Set.of(
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 2),
                        LocalDate.of(2022, 1, 5), 10000),
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 5),
                        LocalDate.of(2022, 1, 16), 3000),
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 16),
                        LocalDate.of(2022, 1, 20), 5000),
                new Price("bublik", 2, 2,
                        LocalDate.of(2022, 1, 5),
                        LocalDate.of(2022, 1, 16), 555),
                new Price("lapsha", 1, 2,
                        LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 1, 3), 20000),
                new Price("lapsha", 1, 2,
                        LocalDate.of(2022, 1, 3),
                        LocalDate.of(2022, 1, 23), 18000),
                new Price("pomidor", 1, 2,
                        LocalDate.of(2022, 1, 3),
                        LocalDate.of(2022, 1, 23), 666)
        );

        setBublikOne = Set.of(
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 2),
                        LocalDate.of(2022, 1, 13), 10000),
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 13),
                        LocalDate.of(2022, 1, 20), 5000),
                new Price("bublik", 1, 2,
                        LocalDate.of(2022, 1, 5),
                        LocalDate.of(2022, 1, 16), 3000)
        );

        setBublikTwo = Set.of(
                new Price("bublik", 2, 2,
                        LocalDate.of(2022, 1, 5),
                        LocalDate.of(2022, 1, 16), 555)
        );

        setLapsha = Set.of(
                new Price("lapsha", 1, 2,
                        LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 1, 10), 20000),
                new Price("lapsha", 1, 2,
                        LocalDate.of(2022, 1, 3),
                        LocalDate.of(2022, 1, 23), 18000)
        );

        setPomidor = Set.of(
                new Price("pomidor", 1, 2,
                        LocalDate.of(2022, 1, 3),
                        LocalDate.of(2022, 1, 23), 666)
        );

        map = Map.of(
                new Price("bublik", 1, 2), setBublikOne,
                new Price("bublik", 2, 2), setBublikTwo,
                new Price("lapsha", 1, 2), setLapsha,
                new Price("pomidor", 1, 2), setPomidor
        );
    }

    @Test
    public void setsToMapCoversionTest() {
        Map<Price, Set<Price>> testMap = PriceService.setsToMap(set1, set2);
        Assert.assertEquals(4, testMap.size());

        for (Map.Entry<Price, Set<Price>> entry : testMap.entrySet()) {
            Set<Price> setFromReference = map.get(entry.getKey());
            Set<Price> setFromConvertorMethod = entry.getValue();

            Assert.assertTrue(setFromReference.containsAll(setFromConvertorMethod) &&
                    setFromConvertorMethod.containsAll(setFromReference));
        }

    }

    @Test
    public void mergingToSetsTest() {
        Collection<Price> merged = PriceService.mergePrices(set1, set2);
        Assert.assertTrue(merged.containsAll(setMerged) && setMerged.containsAll(merged));
    }

}
