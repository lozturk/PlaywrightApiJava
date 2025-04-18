package com.qa.apitests.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.apitests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class contains test cases for creating a user by reading the JSON payload
 * from an external file. The test uses a DataProvider to supply the payload
 * and validates the response against the input data.
 */
public class CreateUserWithJsonFileTests extends BaseTest {

    /**
     * DataProvider method to read the JSON payload from the file and provide it
     * as a byte array to the test method.
     *
     * @return Object[][] containing the JSON payload as a byte array.
     * @throws IOException if the file cannot be read.
     */
    @DataProvider(name = "userDataProvider")
    public Object[][] userDataProvider() throws IOException {
        // Read the JSON payload from the file
        File file = new File("src/test/data/user.json");
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        return new Object[][]{{fileBytes}};
    }

    /**
     * Test method to create a user using the JSON payload provided by the DataProvider.
     * It dynamically updates the email field to ensure uniqueness and validates the
     * response against the input data.
     *
     * @param userDataBytes JSON payload as a byte array.
     * @throws IOException if there is an error processing the JSON data.
     */
    @Test(dataProvider = "userDataProvider")
    public void createUserWithJsonFileTest(byte[] userDataBytes) throws IOException {

        // Generate a unique email address for the user
        String email = "john.doe" + System.currentTimeMillis() + "@example.com";

        // Convert the byte array to a JsonNode object for easy manipulation
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode userData = objectMapper.readTree(userDataBytes);

        // Update the email field in the JSON payload to ensure uniqueness
        ((ObjectNode) userData).put("email", email);

        System.out.println("User data: " + userData.toPrettyString());

        // Retrieve the API token from system properties
        String token = System.getProperty("gorest.api.key");

        // Send a POST request to create the user with the updated JSON payload
        APIResponse response = context.post("/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(userData.toString()));

        // Assert that the HTTP status code is 201 (Created)
        Assert.assertEquals(response.status(), 201, "Status code is not 201");

        // Assert that the response is marked as OK (successful)
        Assert.assertTrue(response.ok(), "Response is not OK");

        // Parse the response body into a JsonNode object for validation
        JsonNode jsonNode = objectMapper.readTree(response.body());

        // Print the formatted JSON response body
        System.out.println("POST Response body: " + jsonNode.toPrettyString());

        // Validate that the response contains the expected user details
        Assert.assertEquals(jsonNode.get("name").asText(), userData.get("name").asText(), "User name does not match");
        Assert.assertEquals(jsonNode.get("email").asText(), userData.get("email").asText(), "User email does not match");
        Assert.assertEquals(jsonNode.get("gender").asText(), userData.get("gender").asText(), "User gender does not match");
        Assert.assertEquals(jsonNode.get("status").asText(), userData.get("status").asText(), "User status does not match");
    }
}