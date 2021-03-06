package triangulation.elements;

import java.math.BigDecimal;

public class Precision {

    private final static double EPSILON = 1.0E-10;
    private final static double VALUE_TYPE_SEPARATOR = 1.0E-9;
    private final static BigDecimal bigEpsilon = BigDecimal.valueOf(EPSILON);

    public static double epsilon() {
        return EPSILON;
    }

    public static double valueFactor() {
        return VALUE_TYPE_SEPARATOR;
    }

    public static BigDecimal bigEpsilon() {
        return bigEpsilon;
    }
}
