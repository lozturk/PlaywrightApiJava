package com.qa.apitests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class GetApiCall extends BaseTest {


    @Test
    public void getUserApiTests() throws IOException {
        // Sends a GET request to the "/public/v2/users" endpoint and stores the response
        APIResponse response = context.get("/public/v2/users");

        // Prints the HTTP status code of the response
        System.out.println("Response status: " + response.status());

        // Asserts that the HTTP status code is 200 (OK)
        Assert.assertEquals(response.status(), 200, "Status code is not 200");

        // Asserts that the response is marked as OK (successful)
        Assert.assertTrue(response.ok(), "Response is not OK");

        // Asserts that the HTTP status text is "OK"
        Assert.assertEquals(response.statusText(), "OK", "Status text is not OK");

        // Creates an ObjectMapper instance to parse the JSON response body
        ObjectMapper objectMapper = new ObjectMapper();

        // Parses the response body into a JsonNode object
        JsonNode jsonNode = objectMapper.readTree(response.body());

        // Converts the JsonNode object to a pretty-printed JSON string
        String jsonString = jsonNode.toPrettyString();

        // Prints the formatted JSON response body
        System.out.println("Response body: " + jsonString);

        // Defines the expected URL for the API response
        String expectedUrl = "https://gorest.co.in/public/v2/users";

        // Asserts that the actual response URL matches the expected URL
        Assert.assertEquals(response.url(), expectedUrl, "Response URL does not match the expected URL");

        // Retrieves the response headers as a Map
        Map<String, String> headers = response.headers();

        // Prints the response headers
        System.out.println("Response headers: " + headers);

        // Asserts that the "content-type" header matches the expected value
        Assert.assertEquals(headers.get("content-type"), "application/json; charset=utf-8", "Response header: content-type does not match the expected value");

        // Asserts that the "date" header is present in the response
        Assert.assertTrue(headers.containsKey("date"), "Response header: date is missing");
    }


    @Test
    public void getSpecificUserApiTest() throws IOException {
        APIResponse response = context.get("/public/v2/users",
                RequestOptions
                        .create()
                        .setQueryParam("id", 7824146)
                        .setQueryParam("name", "Rev. Yogendra Desai"));

        // Asserts that the HTTP status code is 200 (OK)
        Assert.assertEquals(response.status(), 200, "Status code is not 200");

        // Asserts that the response is marked as OK (successful)
        Assert.assertTrue(response.ok(), "Response is not OK");

        // Asserts that the HTTP status text is "OK"
        Assert.assertEquals(response.statusText(), "OK", "Status text is not OK");

        // Creates an ObjectMapper instance to parse the JSON response body
        ObjectMapper objectMapper = new ObjectMapper();

        // Parses the response body into a JsonNode object
        JsonNode jsonNode = objectMapper.readTree(response.body());

        // Converts the JsonNode object to a pretty-printed JSON string
        String jsonString = jsonNode.toPrettyString();

        // Prints the formatted JSON response body
        System.out.println("Response body: " + jsonString);

        // Extracts the first user object from the JSON array
        JsonNode firstUser = jsonNode.get(0);

        // Asserts that the "id" field matches the expected value
        Assert.assertEquals(firstUser.get("id").asInt(), 7824146, "User ID does not match");

        // Asserts that the "name" field matches the expected value
        Assert.assertEquals(firstUser.get("name").asText(), "Rev. Yogendra Desai", "User name does not match");

        // Asserts that the "email" field matches the expected value
        Assert.assertEquals(firstUser.get("email").asText(), "rev_yogendra_desai@mann.example", "User email does not match");

        // Asserts that the "gender" field matches the expected value
        Assert.assertEquals(firstUser.get("gender").asText(), "male", "User gender does not match");

        // Asserts that the "status" field matches the expected value
        Assert.assertEquals(firstUser.get("status").asText(), "active", "User status does not match");






    }

}