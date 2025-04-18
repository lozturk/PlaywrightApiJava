package com.qa.apitests.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.apitests.BaseTest;
import com.qa.utils.Users;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class contains test cases for creating a user using the Users POJO with Lombok annotations.
 * It validates the response to ensure the user is created successfully and that the response contains an ID.
 */
public class CreateUserWithPojoLombokTest extends BaseTest {

    @Test
    public void createUserPostCallWithPojoLombokTest() throws Exception {

        // Generate a unique email address for the user
        String email = "john.doe" + System.currentTimeMillis() + "@example.com";

        // Create a Users object with the required fields using Lombok's builder pattern
        Users user = Users.builder()
                .name("John Doe")
                .email(email)
                .gender("male")
                .status("active")
                .build();

        // Retrieve the API token from system properties
        String token = System.getProperty("gorest.api.key");

        // Send a POST request to create the user
        ObjectMapper objectMapper = new ObjectMapper();
        APIResponse response = context.post("/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(user));

        // Assert that the HTTP status code is 201 (Created)
        Assert.assertEquals(response.status(), 201, "Status code is not 201");

        // Assert that the response is marked as OK (successful)
        Assert.assertTrue(response.ok(), "Response is not OK");

        // Deserialize the response body into a Users object
        Users createdUser = objectMapper.readValue(response.body(), Users.class);

        // Print the created user details
        System.out.println("Created User: " + createdUser);

        // Validate that the response contains an ID
        Assert.assertNotNull(createdUser.getId(), "ID is null in the response");

        // Validate that the response contains the expected user details
        Assert.assertEquals(createdUser.getName(), user.getName(), "User name does not match");
        Assert.assertEquals(createdUser.getEmail(), user.getEmail(), "User email does not match");
        Assert.assertEquals(createdUser.getGender(), user.getGender(), "User gender does not match");
        Assert.assertEquals(createdUser.getStatus(), user.getStatus(), "User status does not match");
    }
}