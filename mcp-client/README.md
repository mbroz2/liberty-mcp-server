# MCP Client (Quarkus)

This project provides a Quarkus-based MCP (Model Context Protocol) client application with an AI chatbot interface. The client connects to an MCP server running on Liberty to access weather forecast tools.

## Key Components

- `AiWeatherService`: Interface that defines the AI service with MCP tool integration
- `ChatBotWebSocket`: Handles WebSocket communication for the chat interface

## Configuration

The application is configured in `application.properties`:

```properties
# MCP Server connection
quarkus.langchain4j.mcp.weather.transport-type=streamable-http
quarkus.langchain4j.mcp.weather.url=http://localhost:9080/mcp-liberty-server/mcp

# Ollama LLM configuration
quarkus.langchain4j.chat-model.provider=ollama
quarkus.langchain4j.ollama.chat-model.model-id=gpt-oss:20b
```

## Prerequisites

- Java 17+
- Maven 3.8.1+
- Ollama

## Running the Application

1. Start the Liberty MCP server (see mcp-liberty-server README)

2. Run the Quarkus application:
   ```bash
   ./mvnw quarkus:dev
   ```

3. Access the application at http://localhost:8080