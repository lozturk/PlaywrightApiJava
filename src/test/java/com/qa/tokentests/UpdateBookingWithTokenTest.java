package com.qa.tokentests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UpdateBookingWithTokenTest {

    private APIRequestContext apiRequestContext;
    private static String TOKEN_ID;
    private ObjectMapper objectMapper;
    int bookingID; // Generate a random booking ID

    @BeforeTest
    public void setUp() throws Exception {
        // Initialize Playwright and create an APIRequest context
        apiRequestContext = Playwright.create().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://restful-booker.herokuapp.com"));

        // Fetch first booking ID from the API
        objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(apiRequestContext.get("https://restful-booker.herokuapp.com/booking").body());
        bookingID = jsonNode.get(0).get("bookingid").asInt();
        System.out.println("Booking ID: " + bookingID);

        // Retrieve the token
        String jsonPayload = "{ \"username\": \"admin\", \"password\": \"password123\" }";
        APIResponse response = apiRequestContext.post("/auth",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(jsonPayload));

        Assert.assertEquals(response.status(), 200, "Failed to retrieve token");
        objectMapper = new ObjectMapper();
        JsonNode responseBody = objectMapper.readTree(response.body());
        TOKEN_ID = responseBody.get("token").asText();

        Assert.assertNotNull(TOKEN_ID, "Token is null");
        Assert.assertFalse(TOKEN_ID.isEmpty(), "Token is empty");
        System.out.println("Retrieved Token: " + TOKEN_ID);
    }

    @AfterTest
    public void tearDown() {
        // Dispose of the APIRequest context
        if (apiRequestContext != null) {
            apiRequestContext.dispose();
        }
    }

    @Test
    public void updateAndValidateBooking() throws Exception {



        // JSON payload for the PUT request
        String jsonPayload = "{\n" +
                "    \"firstname\": \"John\",\n" +
                "    \"lastname\": \"Smith\",\n" +
                "    \"totalprice\": 111,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2018-01-01\",\n" +
                "        \"checkout\": \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\": \"Lunch\"\n" +
                "}";

        // Send a PUT request to update the booking
        APIResponse updateResponse = apiRequestContext.put("/booking/" + bookingID,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Accept", "application/json")
                        .setHeader("Cookie", "token=" + TOKEN_ID)
                        .setData(jsonPayload));

        Assert.assertEquals(updateResponse.status(), 200, "Failed to update booking");
        System.out.println("Update Response: " + objectMapper.readTree(updateResponse.body()).toPrettyString());

        // Send a GET request to validate the update
        APIResponse validateResponse = apiRequestContext.get("/booking/" + bookingID,
                RequestOptions.create()
                        .setHeader("Accept", "application/json"));

        Assert.assertEquals(validateResponse.status(), 200, "Failed to validate booking update");
        objectMapper = new ObjectMapper();
        JsonNode responseBody = objectMapper.readTree(validateResponse.body());
        Assert.assertEquals(responseBody.get("additionalneeds").asText(), "Lunch", "Additional needs not updated");
        System.out.println("Validated Response: " + responseBody.toPrettyString());
    }

    @Test(dependsOnMethods = "updateAndValidateBooking")
    public void deleteBookingTest() throws Exception {
        // Send a DELETE request to delete the booking
        APIResponse deleteResponse = apiRequestContext.delete("/booking/" + bookingID,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Cookie", "token=" + TOKEN_ID));

        // Assert that the HTTP status code is 201 (Created)
        Assert.assertEquals(deleteResponse.status(), 201, "Failed to delete booking");
        // Assert that the response is marked as OK (successful)
        Assert.assertTrue(deleteResponse.ok(), "Response is not OK");
        // Print the response body
        Assert.assertEquals(deleteResponse.text().trim(), "Created", "Response body is not empty");
        System.out.println("Delete Response: " + deleteResponse.text());


        // Print confirmation of deletion
        System.out.println("Booking deleted successfully.");
    }
}