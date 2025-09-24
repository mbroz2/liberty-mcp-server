package com.demo.mcp;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(baseUri = "https://api.open-meteo.com/")
@Path("/v1")
public interface WeatherClient {

    @GET
    @Path("forecast")
    String getForecast(@QueryParam("latitude") double latitude,
                       @QueryParam("longitude") double longitude,
                       @QueryParam("forecast_days") int days,
                       @QueryParam("hourly") String hourly,
                       @QueryParam("temperature_unit") String tempUnit,
                       @QueryParam("wind_speed_unit") String windUnit,
                       @QueryParam("precipitation_unit") String precipitationUnit,
                       @QueryParam("timezone") String timezone);
}