import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public class ApiUnitTest extends Assert {

    /**
     * Тест работоспособности API
     */
    @Test
    public void testAPI() {
        System.err.print("testAPI");

        Method method;
        try {
            method = Class.forName("CalculateDiffrence").getDeclaredMethod("getJSON", String.class);
            method.setAccessible(true);
            double actualCourseVal = 69.015887;

            assertEquals(
                    ((JSONObject)method.invoke(
                            new CalculateDiffrence(new BigDecimal("555.0"), "2016-10-10"),
                            "http://data.fixer.io/api/2016-10-10?access_key=2e319526045774c54d937509b31d70e3&symbols=RUB")).getJSONObject("rates").get("RUB"),
                    actualCourseVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка корректности комиссии при обмене валют
     */
    @Test
    public void spreadCheck() {
        System.err.print("spreadCheck");

        BigDecimal testAnswer = new BigDecimal("0.5");
        BigDecimal eurosQuantity = new BigDecimal("1");

        BigDecimal rateFromJSONCurrentDate = new BigDecimal("100");
        BigDecimal rateFromJSONCustomDate = new BigDecimal("100");

        rateFromJSONCurrentDate =
                rateFromJSONCustomDate
                .subtract(rateFromJSONCurrentDate.divide(new BigDecimal("100"))
                .multiply(new BigDecimal("0.5")));

        BigDecimal finalValue = (rateFromJSONCurrentDate.subtract(rateFromJSONCustomDate)).multiply(eurosQuantity);

        assertEquals(testAnswer, finalValue.abs());
    }
}
