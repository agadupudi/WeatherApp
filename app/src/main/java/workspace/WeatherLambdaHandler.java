package workspace;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

public class WeatherLambdaHandler implements RequestHandler<Map<String, Object>, String> {
    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        String location = (String) input.getOrDefault("location", "London");
        WeatherService service = createWeatherService();
        try {
            return service.getWeather(location);
        } catch (Exception e) {
            return "Error fetching weather: " + e.getMessage();
        }
    }

    protected WeatherService createWeatherService() {
        return new WeatherService();
    }
}
