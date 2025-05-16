package com.qa.apitests.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.apitests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateUserWithHashMapTests extends BaseTest {

    @Test
    public void createUserApiTest() throws IOException {

        String email = "john.doe" + System.currentTimeMillis() + "@example.com";

        // Define the request payload for creating a new user with Hash Map
        Map<String, Object> requestBodyWithHashMap = new HashMap<>();
        requestBodyWithHashMap.put("name", "John Doe");
        requestBodyWithHashMap.put("email", email);
        requestBodyWithHashMap.put("gender", "male");
        requestBodyWithHashMap.put("status", "active");

        // Retrieve the token from VM options
        String token = System.getProperty("gorest.api.key");

        // Send a POST request to the "/public/v2/users" endpoint with the request body
        APIResponse response = apiRequestContext.post("/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(requestBodyWithHashMap));

        // Assert that the HTTP status code is 201 (Created)
        Assert.assertEquals(response.status(), 201, "Status code is not 201");

        // Assert that the response is marked as OK (successful)
        Assert.assertTrue(response.ok(), "Response is not OK");

        // Parse the response body into a JsonNode object
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());

        // Print the formatted JSON response body
        System.out.println("POST Response body: " + jsonNode.toPrettyString());

        // Assert that the response contains the expected user details
        Assert.assertEquals(jsonNode.get("name").asText(), "John Doe", "User name does not match");
        Assert.assertEquals(jsonNode.get("email").asText(), email, "User email does not match");
        Assert.assertEquals(jsonNode.get("gender").asText(), "male", "User gender does not match");
        Assert.assertEquals(jsonNode.get("status").asText(), "active", "User status does not match");

        // GET Call : Fetch the created user based on the ID returned in the POST response

        // Extract the user ID from the response
        int userId = jsonNode.get("id").asInt();

        // Fetch the created user with a GET call
        APIResponse getUserResponse = apiRequestContext.get("/public/v2/users/" + userId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token));

        // Assert that the HTTP status code is 200 (OK)
        Assert.assertEquals(getUserResponse.status(), 200, "Status code is not 200");

        // Parse the GET response body into a JsonNode object
        JsonNode getUserJsonNode = objectMapper.readTree(getUserResponse.body());

        // Print the formatted JSON response body of the GET call
        System.out.println("GET Response body: " + getUserJsonNode.toPrettyString());

        // Assert that the fetched user details match the created user
        Assert.assertEquals(getUserJsonNode.get("id").asInt(), userId, "Fetched user ID does not match");
        Assert.assertEquals(getUserJsonNode.get("name").asText(), "John Doe", "Fetched user name does not match");
        Assert.assertEquals(getUserJsonNode.get("email").asText(), email, "Fetched user email does not match");
        Assert.assertEquals(getUserJsonNode.get("gender").asText(), "male", "Fetched user gender does not match");
        Assert.assertEquals(getUserJsonNode.get("status").asText(), "active", "Fetched user status does not match");


    }
}