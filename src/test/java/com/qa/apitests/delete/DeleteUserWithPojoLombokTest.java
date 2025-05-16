package com.qa.apitests.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.apitests.BaseTest;
import com.qa.utils.Users;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * This class contains test cases for deleting a user using the Users POJO with Lombok annotations.
 * It first creates a user via a POST call, deletes the user via a DELETE call, and validates the deletion with a GET call.
 */
@Slf4j // Enables logging for this class
public class DeleteUserWithPojoLombokTest extends BaseTest {

    @Test // Marks this method as a test case
    public void deleteUserWithPojoLombokTest() throws Exception {

        // Retrieve the API token from system properties
        String token = System.getProperty("gorest.api.key");

        // Step 1: Create a user using a POST call
        String email = "john.doe" + System.currentTimeMillis() + "@example.com"; // Generate a unique email
        Users user = Users.builder() // Use Lombok's builder to create a user object
                .name("John Done") // Set the user's name
                .email(email) // Set the user's email
                .gender("male") // Set the user's gender
                .status("active") // Set the user's status
                .build();

        ObjectMapper objectMapper = new ObjectMapper(); // Create an ObjectMapper instance for JSON handling
        APIResponse postResponse = apiRequestContext.post("/public/v2/users", // Send a POST request to create the user
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json") // Set the content type to JSON
                        .setHeader("Authorization", "Bearer " + token) // Add the authorization token
                        .setData(user)); // Set the user object as the request body

        Assert.assertEquals(postResponse.status(), 201, "POST call failed"); // Assert that the POST call was successful
        Users createdUser = objectMapper.readValue(postResponse.body(), Users.class); // Deserialize the response to a Users object
        int userId = createdUser.getId(); // Extract the user ID from the response
        System.out.println("Created User: " + createdUser); // Print the created user details

        // Step 2: Delete the user using a DELETE call
        APIResponse deleteResponse = apiRequestContext.delete("/public/v2/users/" + userId, // Send a DELETE request to delete the user
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token)); // Add the authorization token

        Assert.assertEquals(deleteResponse.status(), 204, "DELETE call failed"); // Assert that the DELETE call was successful
        Assert.assertEquals(deleteResponse.statusText(), "No Content", "DELETE call failed"); // Assert that the DELETE call was successful
        System.out.println("User with ID " + userId + " deleted successfully."); // Print a success message

        // Step 3: Validate the user is deleted using a GET call
        APIResponse getResponse = apiRequestContext.get("/public/v2/users/" + userId, // Send a GET request to fetch the deleted user
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token)); // Add the authorization token

        // Assert that the GET call returns 404
        Assert.assertEquals(getResponse.status(), 404, "GET call did not return 404 for deleted user");
        // Assert that the GET call returns "Not Found"
        Assert.assertEquals(getResponse.statusText(), "Not Found", "GET call did not return expected status text for deleted user");
        // Assert that the GET call returns the expected error message
        Assert.assertEquals(objectMapper.readTree(getResponse.body()).toString(), "{\"message\":\"Resource not found\"}",
                "GET call did not return expected error message");
        System.out.println("Validation successful: User with ID " + userId + " is deleted."); // Print a validation success message
    }
}