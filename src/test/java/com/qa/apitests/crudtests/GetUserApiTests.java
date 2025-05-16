package com.qa.apitests.crudtests;


import com.qa.apis.GetUserApi;
import com.qa.apitests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.io.IOException;
import static com.qa.constants.ApiPaths.*;

public class GetUserApiTests extends BaseTest {

    private GetUserApi getUserApi;
    private int userId;

    @Test
    public void getUsersTest() throws IOException {
        this.getUserApi = new GetUserApi(apiRequestContext);
        var getUsersApiResponse = getUserApi.getUsers(GET_USERS.getApiPath());
        Assert.assertEquals(getUsersApiResponse.status(), 200, "Status code is not 200");
        var getUsersApiResponseBody = getUserApi.getJsonNode(getUsersApiResponse);
        var responseBody = getUsersApiResponseBody.toPrettyString();
        System.out.println("Response body: " + responseBody);
    }

    @Ignore
    @Test
    public void getUserDetailsByIdTest() throws IOException {
        this.getUserApi = new GetUserApi(apiRequestContext);
        var getUsersApiResponse = getUserApi.getUsers(GET_USERS.getApiPath());
        var getUsersApiResponseBody = getUserApi.getJsonNode(getUsersApiResponse);
        userId = getUsersApiResponseBody.get(0).get("id").asInt();
        var getUserByIdApiResponse = getUserApi.getUserById(GET_USERS.getApiPath(), userId);
        Assert.assertEquals(getUserByIdApiResponse.status(), 200, "Status code is not 200");
        System.out.println(getUserByIdApiResponse.url());
        var getUserByIdApiResponseBody = getUserApi.getJsonNode(getUserByIdApiResponse);
        var responseBody = getUserByIdApiResponseBody.toPrettyString();
        Assert.assertTrue(responseBody.contains(String.valueOf(userId)), "User ID not found in response");
        System.out.println("Response body: " + responseBody);
    }





}
