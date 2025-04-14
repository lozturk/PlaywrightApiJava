package com.qa.apitests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * BaseTest class provides a common setup and teardown mechanism for API tests.
 * It initializes the Playwright instance and APIRequestContext for making API requests.
 */
public class BaseTest {

    // Base URL for the API requests
    protected static final String BaseURL = "https://gorest.co.in";

    // Playwright instance for managing API interactions
    protected Playwright playwright;

    // APIRequest instance for creating request contexts
    protected APIRequest request;

    // APIRequestContext instance for executing API requests
    protected APIRequestContext context;

    /**
     * Initializes the Playwright instance and APIRequestContext before each test.
     * The APIRequestContext is configured with the base URL for the API.
     */
    @BeforeTest
    public void setUp() {
        // Create a Playwright instance
        playwright = Playwright.create();

        // Create an APIRequest instance
        request = playwright.request();

        // Create a new APIRequestContext with the specified base URL
        context = request.newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BaseURL));
    }

    /**
     * Cleans up resources after each test.
     * Disposes of the APIRequestContext and closes the Playwright instance.
     */
    @AfterTest
    public void tearDown() {
        // Dispose of the APIRequestContext to release resources
        if (context != null) {
            context.dispose();
        }

        // Close the Playwright instance to clean up resources
        if (playwright != null) {
            playwright.close();
        }
    }
}