package workspace;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import com.amazonaws.services.lambda.runtime.Context;
import java.util.HashMap;
import java.util.Map;

class WeatherLambdaHandlerTest {
    private WeatherLambdaHandler handler;
    private WeatherService mockService;
    private Context mockContext;

    @BeforeEach
    void setUp() {
        handler = new WeatherLambdaHandler();
        mockService = Mockito.mock(WeatherService.class);
        mockContext = Mockito.mock(Context.class);
    }

    @Test
    void testHandleRequestReturnsWeather() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("location", "Paris");
        WeatherLambdaHandler handlerSpy = Mockito.spy(handler);
        Mockito.doReturn(mockService).when(handlerSpy).createWeatherService();
        Mockito.when(mockService.getWeather("Paris")).thenReturn("Sunny");
        String result = handlerSpy.handleRequest(input, mockContext);
        assertEquals("Sunny", result);
    }

    @Test
    void testHandleRequestHandlesException() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("location", "Paris");
        WeatherLambdaHandler handlerSpy = Mockito.spy(handler);
        Mockito.doReturn(mockService).when(handlerSpy).createWeatherService();
        Mockito.when(mockService.getWeather("Paris")).thenThrow(new RuntimeException("API error"));
        String result = handlerSpy.handleRequest(input, mockContext);
        assertTrue(result.contains("Error fetching weather"));
    }
}
