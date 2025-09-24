package com.demo.mcp;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.openliberty.mcp.annotations.Tool;
import io.openliberty.mcp.annotations.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WeatherTools {
    
    @Inject
    @RestClient
    private WeatherClient weatherClient;
    
    @Tool(name = "getForecast", description = "Provides 7-day weather forecast for a location, with data in 1 hour increments.")
    public String getForecast(@ToolArg(name = "latitude", description = "Latitude of the location") String latitude,
                              @ToolArg(name = "longitude", description = "Longitude of the location") String longitude) {
        System.out.println("Liberty MCP Server: getForecast Tool called");
        return weatherClient.getForecast(
                Double.parseDouble(latitude),
                Double.parseDouble(longitude),
                7,
                "temperature_2m,snowfall,rain,precipitation,precipitation_probability",
                "fahrenheit",
                "mph",
                "inch",
                "auto");
    }
}