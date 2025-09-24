package com.demo.mcp;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;

@RegisterAiService
public interface AiWeatherService {

    @SystemMessage("""
        You are a weather expert. The user will give you a location, and you should first
        get the coordinates for that location, and then based on the coordinates,
        get the weather for that specific location.
        If there are any weather alerts for the location, include them in the response.
        The only formatting allowed is the following html tags: 
          b, strong, i, em, u, del, small, big, sup, sub, p, h1, h2, h3, h4, h5, h6, br, hr, ul, ol, li
        """)
    @McpToolBox
    String getWeather(String message);
}
