package com.qa.apitests.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.apitests.BaseTest;
import com.qa.utils.Users;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class contains test cases for updating a user using the Users POJO with Lombok annotations.
 * It first retrieves the user ID via a GET call and then updates the user via a PUT call.
 */
@Slf4j
public class UpdateUserWithPojoLombokTest extends BaseTest {

    @Test
    public void updateUserWithPojoLombokTest() throws Exception {

        // Retrieve the API token from system properties
        String token = System.getProperty("gorest.api.key");

        // Make a GET call to retrieve the user details
        APIResponse getResponse = apiRequestContext.get("/public/v2/users",
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token));

        // Assert that the GET call is successful
        Assert.assertEquals(getResponse.status(), 200, "GET call failed");

        // Deserialize the response to get the first user's ID
        ObjectMapper objectMapper = new ObjectMapper();
        Users[] users = objectMapper.readValue(getResponse.body(), Users[].class);
        int userId = users[0].getId();
        String userName = users[0].getName();
        String userEmail = users[0].getEmail();
        String userGender = users[0].getGender();
        String userStatus = users[0].getStatus();
        System.out.println("Get exising User parameters : " +  userId +
                ", userName: " + userName +
                ", userEmail: " + userEmail +
                ", userGender: " + userGender +
                ", userStatus: " + userStatus);

        // Create a Users object with the updated details - update user status only
        Users updatedUser = new Users();
        updatedUser.setId(userId);
        updatedUser.setName(userName);
        updatedUser.setEmail(userEmail);
        updatedUser.setGender(userGender);
        if (userStatus.equalsIgnoreCase("active")) {
            updatedUser.setStatus("inactive");
        } else {
            updatedUser.setStatus("active");
        }

        // Print the updated user details before making the PUT call
        System.out.println("Updated User before making the PUT call: " + updatedUser);

        // Make a PUT call to update the user
        APIResponse putResponse = apiRequestContext.put("/public/v2/users/" + userId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(updatedUser));

        // Assert that the PUT call is successful
        Assert.assertEquals(putResponse.status(), 200, "PUT call failed");

        // Deserialize the response body into a Users object
        Users responseUser = objectMapper.readValue(putResponse.body(), Users.class);

        // Print the updated user details
        System.out.println("Updated User after  making the PUT call: " + responseUser);

        // Validate that the response contains the expected updated user details
        Assert.assertEquals(responseUser.getId(), updatedUser.getId(), "User ID does not match");
        Assert.assertEquals(responseUser.getName(), updatedUser.getName(), "User name does not match");
        Assert.assertEquals(responseUser.getEmail(), updatedUser.getEmail(), "User email does not match");
        Assert.assertEquals(responseUser.getGender(), updatedUser.getGender(), "User gender does not match");
        Assert.assertEquals(responseUser.getStatus(), updatedUser.getStatus(), "User status does not match");
    }
}