import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Price implements Comparable<Price> {
    private long id;                    // идентификатор в БД
    private String productCode;         // код товара
    private int number;                 // номер цены
    private int depart;                 // номер отдела
    private LocalDate begin;            // начало действия
    private LocalDate end;              // конец действия
    private long value;                 // значение цены в копейках

    public Price(String productCode, int number, int depart, LocalDate begin, LocalDate end, long value) {
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    public Price(String productCode, int number, int depart) {
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
    }

    @Override
    public int compareTo(Price o) {
        if (productCode.compareTo(o.productCode) != 0) {
            return productCode.compareTo(o.getProductCode());
        } else if (number != o.getNumber()) {
            return o.getNumber() < number ? 1 : -1;
        } else if (depart != o.getDepart()) {
            return o.getDepart() < depart ? 1 : -1;
        } else if (!begin.equals(o.getBegin())) {
            return begin.compareTo(o.getBegin());
        } else if (!end.equals(o.getEnd())) {
            return end.compareTo(o.getEnd());
        } else if (value != o.getValue()) {
            return o.getValue() < value ? 1 : -1;
        } else {
            return 0;
        }
    }
}
