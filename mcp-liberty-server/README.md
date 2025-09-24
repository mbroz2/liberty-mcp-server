# Liberty MCP Server

This project provides an Open Liberty-based MCP (Model Context Protocol) server that exposes weather forecast tools to MCP clients. The server acts as a bridge between AI applications and business logic/data.

## Technical Overview

The application uses:
- **Open Liberty**: A lightweight, open-source Java runtime
- **MicroProfile**: For REST client and configuration
- **MCP Server Feature**: Liberty's implementation of the MCP protocol
- **Open-Meteo API**: For retrieving weather forecast data

## Key Components

- `WeatherTools`: Implements the MCP tool for weather forecasts using the `@Tool` annotation
- `WeatherClient`: MicroProfile REST client for the Open-Meteo API
- `server.xml`: Liberty server configuration with MCP feature enabled

## Server Configuration

The Liberty server is configured in `server.xml`:

```xml
<featureManager>
    <feature>microProfile-7.0</feature>
    <feature>mcpServer-1.0</feature>
</featureManager>

<httpEndpoint id="defaultHttpEndpoint"
              httpPort="9080"
              httpsPort="9443" />

<webApplication contextRoot="/mcp-liberty-server" location="mcp-liberty-server.war" />
```

## MCP Tool Implementation

The server exposes a weather forecast tool using the MCP protocol:

```java
@Tool(name = "getForecast", description = "Get weather forecast for a location.")
public String getForecast(@ToolArg(name = "latitude", description = "Latitude of the location") String latitude,
                          @ToolArg(name = "longitude", description = "Longitude of the location") String longitude) {
    // Implementation calls the Open-Meteo API
}
```

## Prerequisites

- Java 17+
- Maven 3.8.1+

## Running the Server

1. Build and run the Liberty server:
   ```bash
   ./mvnw liberty:dev
   ```

2. The server will start on port 9080 and expose the MCP endpoint at:
   ```
   http://localhost:9080/mcp-liberty-server/mcp
   ```

## Development

### Adding New MCP Tools

To add new tools to the MCP server:

1. Create a new class or extend `WeatherTools` with methods annotated with `@Tool`
2. Define the tool parameters using `@ToolArg` annotations
3. Implement the tool logic