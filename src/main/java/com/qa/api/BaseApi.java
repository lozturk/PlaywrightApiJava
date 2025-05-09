package com.qa.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import lombok.Getter;

/**
 * BaseApi class provides a common setup for API tests.
 * It initializes the Playwright instance and APIRequestContext for making API requests.
 */

@Getter
public abstract class BaseApi {

    private final Playwright playwright;
    private final APIRequest apiRequest;
    private final APIRequestContext apiRequestContext;
    private static final String BASE_URL = "https://gorest.co.in";
    private final ObjectMapper objectMapper;
 //   private final RequestOptions requestOptions;

    /**
     * Constructor initializes Playwright and APIRequestContext.
     */


    public BaseApi() {
        // Initialize Playwright and create an APIRequest & APIRequestContext
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BASE_URL));
        this.objectMapper = new ObjectMapper();
 //       this.requestOptions = RequestOptions.create();
    }

    /**
     * Cleans up resources after each test.
     * Disposes of the APIRequestContext and closes the Playwright instance.
     */
    public void tearDown() {
        // Dispose of the APIRequestContext to release resources
        if (apiRequestContext != null) {
            apiRequestContext.dispose();
        }

        // Close the Playwright instance to clean up resources
        if (playwright != null) {
            playwright.close();
        }

    }

    public RequestOptions setQueryParameter(String name, int value) {
        return RequestOptions.create().setQueryParam(name, value);
    }


}
