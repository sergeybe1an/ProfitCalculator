import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class ApiUnitTest extends Assert {

    @Test
    public void testAPI() {
        Method method;
        try {
            method = Class.forName("CalculateDiffrence").getDeclaredMethod("getJSON", String.class);
            method.setAccessible(true);
            double actualCourseVal = 69.015887;

            assertEquals(
                    ((JSONObject)method.invoke(
                            new CalculateDiffrence(555.0, "2016-10-10"),
                            "http://data.fixer.io/api/2016-10-10?access_key=03e03fdf8f553197d3442534b28b9524&symbols=RUB")).getJSONObject("rates").get("RUB"),
                    actualCourseVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
