# Playwright API Testing with Java

This project demonstrates API testing using **Playwright** in Java. It includes test cases for validating API responses, headers, and JSON payloads.

## Project Structure

- **`src/main/java`**: Contains the main application code.
- **`src/test/java`**: Contains the test cases for API testing.
  - `BaseTest.java`: Sets up and tears down the Playwright API request context.
  - `GetApiCall.java`: Contains test cases for GET API calls.

## Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- Internet connection (to access the API)

## Dependencies

The project uses the following dependencies:
- [Playwright](https://playwright.dev/java/) for API testing
- [TestNG](https://testng.org/) for test execution
- [Jackson Databind](https://github.com/FasterXML/jackson-databind) for JSON parsing
