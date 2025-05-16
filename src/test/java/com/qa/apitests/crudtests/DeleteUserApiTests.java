package com.qa.apitests.crudtests;

import com.microsoft.playwright.APIResponse;
import com.qa.apis.CreateUserApi;
import com.qa.apis.DeleteUserApi;
import com.qa.apis.GetUserApi;
import com.qa.apitests.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static com.qa.utils.Constants.*;
import static org.awaitility.Awaitility.await;

@Slf4j
public class DeleteUserApiTests extends BaseTest {

    @Test(description = "Delete user test")
    public void deleteUserTest() throws IOException, InterruptedException {
        // Create a user to delete
        CreateUserApi createUserApi = new CreateUserApi(apiRequestContext);
        String email = "delete.user" + System.currentTimeMillis() + "@example.com";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Delete User");
        requestBody.put("email", email);
        requestBody.put("gender", "male");
        requestBody.put("status", "active");

        APIResponse createResponse = createUserApi.createUser(
                BASE_ENV_PATH,
                AUTHORIZATION_HEADER,
                System.getProperty("token"),
                requestBody
        );
        Assert.assertEquals(createResponse.status(), 201, "User creation failed");
        int userId = createUserApi.getJsonNode(createResponse).get("id").asInt();
        log.info("Created User ID: {}", userId);

        // Delete the user
        DeleteUserApi deleteUserApi = new DeleteUserApi(apiRequestContext);
        APIResponse deleteResponse = deleteUserApi.deleteUser(
                BASE_ENV_PATH,
                AUTHORIZATION_HEADER,
                System.getProperty("token"),
                userId
        );
        Assert.assertEquals(deleteResponse.status(), 204, "User deletion failed");

        // Optionally, verify user is deleted
        GetUserApi getUserApi = new GetUserApi(apiRequestContext);

//        await()
//                .atMost(10, TimeUnit.SECONDS)
//                .pollInterval(1, TimeUnit.SECONDS)
//                .until(() -> {
//                    APIResponse getResponse = getUserApi.getUserById(BASE_ENV_PATH, userId);
//                    return getResponse.status() == 404;
//                });


        await()
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .until(() -> {
                    APIResponse getResponse = getUserApi.getUserById(BASE_ENV_PATH, userId);
                    int status = getResponse.status();
                    log.info("Polling GET for userId {}: status={}", userId, status);
                    if (status != 404) {
                        log.info("Response body: {}", getResponse.text());
                    }
                    return status == 404;
                });


        APIResponse getResponse = getUserApi.getUserById(BASE_ENV_PATH, userId);
        System.out.println("Get User Response: " + getUserApi.getJsonNode(getResponse));
        getUserApi.getJsonNode(getResponse).toPrettyString();
        System.out.println(getResponse.status() +" " + getResponse.statusText());
        Assert.assertEquals(getResponse.status(), 404, "Deleted user still exists");
    }
}