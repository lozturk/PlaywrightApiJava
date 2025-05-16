package com.qa.apitests.get;

import com.microsoft.playwright.APIResponse;
import com.qa.apitests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * APIResponseHeadersTest class validates the headers of API responses.
 */
public class APIResponseHeadersTest extends BaseTest {

    @Test
    public void getHeadersTest() {
        // Send a GET request to the "/public/v2/users" endpoint
        APIResponse response = apiRequestContext.get("/public/v2/users");

        // Assert that the HTTP status code is 200 (OK)
        Assert.assertEquals(response.status(), 200, "Status code is not 200");

        // Retrieve the response headers as a Map
        Map<String, String> headers = response.headers();

        // Print the response headers
        System.out.println("Response headers: " + headers);

        // Validate specific headers
        Assert.assertTrue(headers.containsKey("content-type"), "Header 'content-type' is missing");
        Assert.assertEquals(headers.get("content-type"), "application/json; charset=utf-8", "Header 'content-type' value is incorrect");

        Assert.assertTrue(headers.containsKey("date"), "Header 'date' is missing");
        Assert.assertNotNull(headers.get("date"), "Header 'date' value is null");
    }
}