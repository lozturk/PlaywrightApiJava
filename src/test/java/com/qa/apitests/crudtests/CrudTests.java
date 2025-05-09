package com.qa.apitests.crudtests;


import com.qa.api.GetUserApi;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import static com.qa.constants.ApiPaths.*;

public class CrudTests {

    private GetUserApi getUserApi;
    private int userId;

    @BeforeClass
    public void setUp() {
        this.getUserApi = new GetUserApi();
    }

    @Test
    public void getUsersTest() throws IOException {
        var getUsersApiResponse = getUserApi.getUsers(GET_USERS.getApiPath());
        Assert.assertEquals(getUsersApiResponse.status(), 200, "Status code is not 200");
        var getUsersApiResponseBody = getUserApi.getJsonNode(getUsersApiResponse);
        String responseBody = getUsersApiResponseBody.toPrettyString();
        System.out.println("Response body: " + responseBody);
        userId = getUsersApiResponseBody.get(0).get("id").asInt();
        getUserApi.tearDown();
    }

    @Test
    public void getUserByIdTest() throws IOException {
        var getUsersApiResponse = getUserApi.getUsers(GET_USERS.getApiPath());
        var getUsersApiResponseBody = getUserApi.getJsonNode(getUsersApiResponse);
        userId = getUsersApiResponseBody.get(0).get("id").asInt();
        var getUserByIdApiResponse = getUserApi.getUserById(GET_USERS.getApiPath(), userId);
        Assert.assertEquals(getUserByIdApiResponse.status(), 200, "Status code is not 200");
        System.out.println(getUserByIdApiResponse.url());
        var getUserByIdApiResponseBody = getUserApi.getJsonNode(getUserByIdApiResponse);
        String responseBody = getUserByIdApiResponseBody.toPrettyString();
        Assert.assertTrue(responseBody.contains(String.valueOf(userId)), "User ID not found in response");
        System.out.println("Response body: " + responseBody);
        getUserApi.tearDown();
    }





}
