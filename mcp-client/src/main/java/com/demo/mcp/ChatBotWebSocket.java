package com.demo.mcp;

import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.smallrye.common.annotation.Blocking;

@WebSocket(path = "/chatbot")
public class ChatBotWebSocket {

    private final AiWeatherService aiWeatherService;

    public ChatBotWebSocket(AiWeatherService aiWeatherService) {
        this.aiWeatherService = aiWeatherService;
    }

    @OnOpen
    public String onOpen() {
        return "Hello, I am the Liberty MCP Server Weather Assistant, how can I help?";
    }

    @OnTextMessage
    @Blocking
    public String onMessage(String message) {
        return aiWeatherService.getWeather(message);
    }

}
