package com.demo.mcp;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.openliberty.mcp.annotations.Tool;
import io.openliberty.mcp.annotations.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/*
 * A class annotated with a CDI managed bean is required for the @Tool annotations to be discovered.
 */
@ApplicationScoped
public class WeatherTools {
    
    @Inject
    @RestClient
    private WeatherClient weatherClient;
    
    @Tool(name = "getForecast", description = "Provides weather forecast for a location, with data in 1 hour increments.")
    public String getForecast(@ToolArg(name = "latitude", description = "Latitude of the location") String latitude,
                              @ToolArg(name = "longitude", description = "Longitude of the location") String longitude,
                              @ToolArg(name = "forecastDays", description = "The number of days for which to provide forecast data for") int forecastDays,
                              @ToolArg(name = "temperature", description = "Whether to provide temperature data, in Fahrenheit.") boolean temperature,
                              @ToolArg(name = "snowfall", description = "Whether to provide snowfall data, in inches.") boolean snowfall,
                              @ToolArg(name = "rain", description = "Whether to provide rain data, in inches.") boolean rain,
                              @ToolArg(name = "precipitation", description = "Whether to provide precipitation data, in inches.") boolean precipitation,
                              @ToolArg(name = "precipitationProbability", description = "Whether to provide precipitation probability data, in percentage.") boolean precipitationProbability) {
        String hourlyData = "";
        if (temperature) {
            hourlyData += "temperature_2m";
        }
        if (snowfall) {
            hourlyData += ",snowfall";
        }
        if (rain) {
            hourlyData += ",rain";
        }
        if (precipitation) {
            hourlyData += ",precipitation";
        }
        if (precipitationProbability) {
            hourlyData += ",precipitation_probability";
        }

        System.out.println("Liberty MCP Server: getForecast Tool called.  Requesting " + forecastDays + " days of hourly data for " + hourlyData + ".");
        
        return weatherClient.getForecast(
                Double.parseDouble(latitude),
                Double.parseDouble(longitude),
                forecastDays,
                hourlyData,
                "fahrenheit",
                "mph",
                "inch",
                "auto");
    }
}