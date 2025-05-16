package com.qa.apitests.crudtests;


import com.qa.apis.CreateUserApi;
import com.qa.apitests.BaseTest;
import com.qa.constants.Constants;
import com.qa.utils.User;
import com.qa.utils.Users;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


public class CreateUserApiTests extends BaseTest {

    private CreateUserApi createUserApi;
    private User user;
    private Users users;

    @DataProvider (name = "userData", parallel = true)
    public Object[][] createUserData() throws IOException {

        // Retrieve the token from VM options
        String token = System.getProperty("token");

        // 1/ Define the request payload for creating a new user with Hash Map
        String email1 = "john.doe1" + System.currentTimeMillis() + "@example.com";
        Map<String, Object> requestBodyWithHashMap = new HashMap<>();
        requestBodyWithHashMap.put("name", "John Doe");
        requestBodyWithHashMap.put("email", email1);
        requestBodyWithHashMap.put("gender", "male");
        requestBodyWithHashMap.put("status", "active");

        // 2/ Define the request payload for creating a new user with JSON file
        String email2 = "john.doe2" + System.currentTimeMillis() + "@example.com";
        File file = new File("src/main/java/com/qa/data/user.json");
        var fileContent = new String(Files.readAllBytes(file.toPath()));
        fileContent = fileContent.replace("\"email\" : \"john.doe1243@example.com\"", "\"email\" : \"" + email2 + "\"");
        byte[] fileBytes = fileContent.getBytes();

        // 3/ Define the request payload for creating a new user with JSON string
        String email3 = "john.doe3" + System.currentTimeMillis() + "@example.com";
        String requestBodyWithJsonString = String.format(
                "{ \"name\": \"John Doe\", \"email\": \"%s\", \"gender\": \"male\", \"status\": \"active\" }",
                email3
        );

        // 4/ Define the request payload for creating a new user with User object
        String email4 = "john.doe4" + System.currentTimeMillis() + "@example.com";
        this.user = new User();
        user.setName("John Doe");
        user.setEmail(email4);
        user.setGender("male");
        user.setStatus("active");

        // 5/ Define the request payload for creating a new user with Users object
        String email5 = "john.doe5" + System.currentTimeMillis() + "@example.com";
        this.users = Users.builder()
                          .name("John Doe")
                          .email(email5)
                          .gender("male")
                          .status("active")
                          .build();


        return new Object[][]{
                {"Authorization", token, requestBodyWithHashMap},
                {"Authorization", token, requestBodyWithJsonString},
                {"Authorization", token, this.user},
                {"Authorization", token, this.users},
                {"Authorization", token, fileBytes},
        };
    }


    @Test (description = "This is create user test", dataProvider = "userData")
    public void createUserTest (String headerKey, String token, Object body) throws IOException {
        this.createUserApi = new CreateUserApi(apiRequestContext);
        var createUsersApiResponse = createUserApi
                .createUser(Constants.BASE_ENV_PATH,headerKey, token, body);
        Assert.assertEquals(createUsersApiResponse.status(), 201, "Status code is not 200");
        var createUsersApiResponseBody = createUserApi.getJsonNode(createUsersApiResponse);
        var responseBody = createUsersApiResponseBody.toPrettyString();
        System.out.println("Response body: " + responseBody);
    }






}
