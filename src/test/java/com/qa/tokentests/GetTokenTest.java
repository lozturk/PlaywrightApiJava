package com.qa.tokentests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.microsoft.playwright.Playwright.create;

/**
 * This class contains a test case for retrieving a token from the specified base URI.
 */
public class GetTokenTest {


    private APIRequestContext apiRequestContext;

    @BeforeClass
    public void setUp() {
        // Initialize Playwright and create an APIRequest context
//        apiRequestContext = Playwright.create().request().newContext();
        // Initialize Playwright and create an APIRequest context
        apiRequestContext = create().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://restful-booker.herokuapp.com"));
    }

    @AfterClass
    public void tearDown() {
        // Dispose of the APIRequest context
        if (apiRequestContext != null) {
            apiRequestContext.dispose();
        }
    }

    @Test
    public void getTokenTest() throws Exception {
        // JSON payload for the POST request
        String jsonPayload = "{ \"username\": \"admin\", \"password\": \"password123\" }";

        // Send a POST request to the /auth endpoint
        APIResponse response = apiRequestContext.post("/auth",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(jsonPayload));

        // Assert that the HTTP status code is 200 (OK)
        Assert.assertEquals(response.status(), 200, "Status code is not 200");

        // Deserialize the response body to extract the token
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseBody = objectMapper.readTree(response.body());
        String token = responseBody.get("token").asText();

        // Assert that the token is not null or empty
        Assert.assertNotNull(token, "Token is null");
        Assert.assertFalse(token.isEmpty(), "Token is empty");

        // Print the retrieved token
        System.out.println("Retrieved Token: " + token);
    }
}