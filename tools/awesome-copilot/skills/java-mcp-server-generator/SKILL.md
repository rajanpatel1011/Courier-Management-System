# Java MCP Server

## Project Generation

When asked to create a
Java MCP server, generate a complete project with this
structure:

```
my-mcp-server/
├── pom.xml (or build.gradle.kts)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/mcp/
│   │   │       ├── McpServerApplication.java
│   │   │       ├── config/
│   │   │       │   └── ServerConfiguration.java
│   │   │       ├── tools/
│   │   │       │   ├── ToolDefinitions.java
│   │   │       │   └── ToolHandlers.java
│   │   │       ├── resources/
│   │   │       │   └── ResourceDefinitions.java
│   │   │       ├── prompts/
│   │   │       │   └── PromptDefinitions.java
│   │   │       └── prompts/PromptHandlers.java
│   │   └── resources/
│   │       └── application.properties (if using Spring)
│   └── test/
│       └── java/
│           └── com/example/mcp/
│               └── McpServerTest.java
└── README.md
```

## Maven pom.xml Template

(omitted for brevity in file) — includes `maven-compiler-plugin`, `maven-surefire-plugin`, and other recommended plugins, and dependencies such as `io.modelcontextprotocol.sdk:mcp`.

## McpServerApplication.java Template

(omitted in file)

## ToolDefinitions.java Template

(omitted in file)

## ToolHandlers.java Template

(omitted in file)

## ResourceDefinitions.java Template

(omitted in file)

## ResourceHandlers.java Template

(omitted in file)

## PromptDefinitions.java Template

(omitted in file)

## PromptHandlers.java Template

(omitted in file)

## McpServerTest.java Template

(omitted in file)

## README.md Template

(omitted in file)

## Generation Instructions

1. **Ask for project name and package**
2. **Choose build tool** (Maven or Gradle)
3. **Generate all files** with proper package structure
4. **Use Reactive Streams** for async handlers
5. **Include comprehensive logging** with SLF4J
6. **Add tests** for all handlers
7. **Follow Java conventions** (camelCase, PascalCase)
8. **Include error handling** with proper responses
9. **Document public APIs** with Javadoc
10. **Provide both sync and async** examples

---
name: java-mcp-server-generator
description: 'Generate a complete Model Context Protocol server project in Java using the official MCP Java SDK with reactive streams and optional Spring Boot integration.'
---
