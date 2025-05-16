package com.qa.apitests.crudtests;

import com.github.javafaker.Faker;
import com.qa.apis.GetUserApi;
import com.qa.apis.UpdateUserApi;
import com.qa.apitests.BaseTest;
import com.qa.utils.Constants;
import com.qa.utils.Users;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static com.qa.utils.Constants.AUTHORIZATION_HEADER;


public class UpdateUserApiTests extends BaseTest {

    private GetUserApi getUserApi;
    private UpdateUserApi updateUserApi;
    private int userId;

    @DataProvider(name = "userData", parallel = true)
    public Object[][] updateUserData() {
        String token = System.getProperty("token");
        Faker faker = new Faker();

        return Stream.generate(() -> {
            String[] genders = {"male", "female"};
            String[] statuses = {"active", "inactive"};
            String name = faker.name().fullName();
            String gender = genders[ThreadLocalRandom.current().nextInt(genders.length)];
            String status = statuses[ThreadLocalRandom.current().nextInt(statuses.length)];
            String email = "updated.user" + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(1000) + "@example.com";

            Users users = Users.builder()
                               .name(name)
                               .email(email)
                               .gender(gender)
                               .status(status)
                               .build();

            return new Object[]{AUTHORIZATION_HEADER, token, users};
        }).limit(3).toArray(Object[][]::new);
    }

    @Test(description = "This is update user test", dataProvider = "userData")
    public void updateUserTest(String headerKey, String token, Users users) throws Exception {
        this.getUserApi = new GetUserApi(apiRequestContext);
        var getUsersApiResponse = getUserApi.getUsers(Constants.BASE_ENV_PATH);
        var getUsersApiResponseBody = getUserApi.getJsonNode(getUsersApiResponse);
        int randomIndex = ThreadLocalRandom.current().nextInt(0, 9);
        this.userId = getUsersApiResponseBody.get(randomIndex).get("id").asInt();
        System.out.println("User ID: " + this.userId);
        System.out.println(getUsersApiResponseBody.get(randomIndex).toPrettyString());
        this.updateUserApi = new UpdateUserApi(apiRequestContext);
        var updateUserApiResponse = updateUserApi
                .updateUser(Constants.BASE_ENV_PATH + this.userId, headerKey, token, users);
        Assert.assertEquals(updateUserApiResponse.status(), 200, "Status code is not 200");
        var updateUserApiResponseBody = updateUserApi.getJsonNode(updateUserApiResponse);
        var responseBody = updateUserApiResponseBody.toPrettyString();
        System.out.println("Response body: " + responseBody);
        Assert.assertEquals(updateUserApiResponseBody.get("name").asText(), users.getName(), "Name is not updated");
        Assert.assertEquals(updateUserApiResponseBody.get("email").asText(), users.getEmail(), "Email is not updated");
        Assert.assertEquals(updateUserApiResponseBody.get("gender").asText(), users.getGender(), "Gender is not updated");
        Assert.assertEquals(updateUserApiResponseBody.get("status").asText(), users.getStatus(), "Status is not updated");
    }

}