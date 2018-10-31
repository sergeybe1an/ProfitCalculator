import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class CalculateDiffrence {

    private BigDecimal eurosQuantity;
    private String partOfURLwithDate;
    private String apiKey;

    public CalculateDiffrence(BigDecimal eurosQuantity, String partOfURLwithDate) {
        this.eurosQuantity = eurosQuantity;
        this.partOfURLwithDate = partOfURLwithDate;

        this.apiKey = getAPIKey();
    }

    String getCalculateResult() throws Exception {

        StringBuilder labelResult = new StringBuilder();

        String urlCurrentDate = "http://data.fixer.io/api/latest?access_key=" + apiKey + "&symbols=RUB";
        String urlCustomDate = "http://data.fixer.io/api/" + partOfURLwithDate + "?access_key=" + apiKey + "&symbols=RUB";

        JSONObject jsonObjectCurrentDate = getJSON(urlCurrentDate);
        JSONObject jsonObjectCustomDate = getJSON(urlCustomDate);

        System.out.println(jsonObjectCustomDate + " " + urlCurrentDate + " " + eurosQuantity + " " + partOfURLwithDate);
        System.out.println(jsonObjectCurrentDate);

        BigDecimal rateFromJSONCurrentDate;
        BigDecimal rateFromJSONCustomDate;
        try {
            rateFromJSONCurrentDate = new BigDecimal(jsonObjectCurrentDate.getJSONObject("rates").getString("RUB"));
            rateFromJSONCustomDate = new BigDecimal(jsonObjectCustomDate.getJSONObject("rates").getString("RUB"));

//            rateFromJSONCurrentDate = new BigDecimal("100");
//            rateFromJSONCustomDate = new BigDecimal("100");

            rateFromJSONCurrentDate =
                            rateFromJSONCustomDate
                            .subtract(rateFromJSONCurrentDate.divide(new BigDecimal("100"))
                            .multiply(new BigDecimal("0.5")));

        } catch (NumberFormatException | NullPointerException /*| JSONException*/ nfe) {
            return "У полученных данных неверный<br/> формат или они отсутствуют(возможно что-то с API-ключом)";
        }

        BigDecimal finalValue = (rateFromJSONCurrentDate.subtract(rateFromJSONCustomDate)).multiply(eurosQuantity);

        labelResult.append(finalValue.compareTo(BigDecimal.ZERO) < 0 ? "Вы потеряете " : "Вы выйдете в плюс ")
                   .append(finalValue.setScale(2, BigDecimal.ROUND_HALF_DOWN).abs().toString())
                   .append(" рублей");

        return labelResult.toString();
    }

    /**
     * @param url
     * @return JSON-объект, результат запроса по API
     * @throws Exception
     */
    private JSONObject getJSON(String url) throws Exception {
        URL urlObj = new URL(url);
        StringBuilder response = new StringBuilder();

        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:61.0) Gecko/20100101 Firefox/61.0");

        try(BufferedReader input = new BufferedReader(
                                   new InputStreamReader(
                                   connection.getInputStream()))) {

            String currentLine;
            while ((currentLine = input.readLine()) != null) {
                response.append(currentLine);
            }
        }

        return new JSONObject(response.toString());
    }

    /**
     * Функция получения ключа для API
     * @return ключ для API
     */
    private String getAPIKey() {
        String key = "";
        String propName = "API_key";

        try(InputStream is = new FileInputStream("settings.properties")) {
            Properties properties = new Properties();
            properties.load(is);

            key = properties.getProperty(propName);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return key;
    }
}
