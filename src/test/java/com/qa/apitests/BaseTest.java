package com.qa.apitests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.qa.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * BaseTest class provides a common setup and teardown mechanism for API tests.
 * It initializes the Playwright instance and APIRequestContext for making API requests.
 */
@Slf4j
public class BaseTest {

    // Playwright instance for managing API interactions
    protected Playwright playwright;

    // APIRequest instance for creating request contexts
    protected APIRequest request;

    // APIRequestContext instance for executing API requests
    protected APIRequestContext apiRequestContext;

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
        apiRequestContext = request.newContext(new APIRequest.NewContextOptions()
                .setBaseURL(Constants.BASE_ENV_URL));
    }

    /**
     * Cleans up resources after each test.
     * Disposes of the APIRequestContext and closes the Playwright instance.
     */
    @AfterTest
    public void tearDown() {
        try {
            if (this.apiRequestContext != null) {
                this.apiRequestContext.dispose();
            }
        } catch (Exception e) {
            log.error("Error disposing APIRequestContext: ", e);
        }
        try {
            if (this.playwright != null) {
                this.playwright.close();
            }
        } catch (Exception e) {
            log.error("Error closing Playwright: ", e);
        }
    }

}