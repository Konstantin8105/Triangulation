package triangulation.geometries;

import java.math.BigDecimal;

public class Value {
    private double valueDouble;
    private BigDecimal valueBig;
    private final boolean isValueDouble;

    Value(double valueDouble) {
        this.valueDouble = valueDouble;
        isValueDouble = true;
    }

    Value(BigDecimal valueBig) {
        this.valueBig = valueBig;
        isValueDouble = false;
    }

    boolean isValueDouble() {
        return isValueDouble;
    }

    BigDecimal getValueBig() {
        return valueBig;
    }

    double getValueDouble() {
        return valueDouble;
    }
}
