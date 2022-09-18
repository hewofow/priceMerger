import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PriceService {

    public static Set<Price> mergePrices(Price oldPrice, Price newPrice) {
        Set<Price> mergedPrices = new HashSet<>();

        if (!oldPrice.getProductCode().equals(newPrice.getProductCode()) ||
                oldPrice.getNumber() != newPrice.getNumber() ||
                oldPrice.getDepart() != newPrice.getDepart()) {
            mergedPrices.add(oldPrice);
            mergedPrices.add(newPrice);
            return mergedPrices;
        }

        // new fully COVERS old
        if (newPrice.getBegin().isBefore(oldPrice.getBegin()) && newPrice.getEnd().isAfter(oldPrice.getEnd())) {
            mergedPrices.add(newPrice);
            // new is fully INSIDE old
        } else if (newPrice.getBegin().isAfter(oldPrice.getBegin()) && newPrice.getBegin().isBefore(oldPrice.getEnd()) &&
                newPrice.getEnd().isBefore(oldPrice.getEnd()) && newPrice.getEnd().isAfter(oldPrice.getBegin())) {

            mergedPrices.add(new Price(oldPrice.getProductCode(), oldPrice.getNumber(), oldPrice.getDepart(),
                    oldPrice.getBegin(), newPrice.getBegin(), oldPrice.getValue()));

            mergedPrices.add(new Price(oldPrice.getProductCode(), oldPrice.getNumber(), oldPrice.getDepart(),
                    newPrice.getBegin(), newPrice.getEnd(), newPrice.getValue()));

            mergedPrices.add(new Price(oldPrice.getProductCode(), oldPrice.getNumber(), oldPrice.getDepart(),
                    newPrice.getEnd(), oldPrice.getEnd(), oldPrice.getValue()));

            // new begins INSIDE old and ends AFTER old
        } else if (newPrice.getBegin().isAfter(oldPrice.getBegin()) && newPrice.getBegin().isBefore(oldPrice.getEnd())) {

            mergedPrices.add(new Price(oldPrice.getProductCode(), oldPrice.getNumber(), oldPrice.getDepart(),
                    oldPrice.getBegin(), newPrice.getBegin(), oldPrice.getValue()));

            mergedPrices.add(new Price(oldPrice.getProductCode(), oldPrice.getNumber(), oldPrice.getDepart(),
                    newPrice.getBegin(), newPrice.getEnd(), newPrice.getValue()));

            // new ends INSIDE old but begins BEFORE old
        } else if (newPrice.getEnd().isBefore(oldPrice.getEnd()) && newPrice.getEnd().isAfter(oldPrice.getBegin())) {

            mergedPrices.add(new Price(oldPrice.getProductCode(), oldPrice.getNumber(), oldPrice.getDepart(),
                    newPrice.getBegin(), newPrice.getEnd(), newPrice.getValue()));

            mergedPrices.add(new Price(oldPrice.getProductCode(), oldPrice.getNumber(), oldPrice.getDepart(),
                    newPrice.getEnd(), oldPrice.getEnd(), oldPrice.getValue()));

        } else {
            mergedPrices.add(oldPrice);
            mergedPrices.add(newPrice);
        }

        return mergedPrices;
    }

    public static Collection<Price> mergePrices(Collection<Price> initialPrices, Collection<Price> newPrices) {
        Collection<Price> result = initialPrices.stream()
                .flatMap(p -> newPrices.stream()
                        .filter(n -> Objects.equals(p.getProductCode(), n.getProductCode())
                                && p.getNumber() == n.getNumber() && p.getDepart() == n.getDepart())
                        .collect(Collectors.toList()).stream()
                        .flatMap(fromFiltered -> mergePrices(p, fromFiltered).stream()))
                .collect(Collectors.toList());

        newPrices.forEach(n -> {
            if (!result.contains(n))
                result.add(n);
        });

        return result;
    }

    public static Map<Price, Set<Price>> setsToMap(Collection<Price> initialPrices, Collection<Price> newPrices) {
        Set<Price> allPrices = new HashSet<>();
        allPrices.addAll(initialPrices);
        allPrices.addAll(newPrices);

        return allPrices.stream()
                .collect(Collectors.toMap(
                        p -> new Price(p.getProductCode(), p.getNumber(), p.getDepart()),
                        Set::of,
                        (existingSet, replacement) -> {
                            Set<Price> mergedSet = new HashSet<>();
                            mergedSet.addAll(existingSet);
                            mergedSet.addAll(replacement);
                            return mergedSet;
                        }
                ));
    }

}
