package com.qa.apitests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    protected Playwright playwright;
    protected APIRequestContext context;

    @BeforeTest
    public void setUp() {
        playwright = Playwright.create();
        APIRequest request = playwright.request();
        context = request.newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://gorest.co.in"));
    }

    @AfterTest
    public void tearDown() {
        if (context != null) {
            context.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}