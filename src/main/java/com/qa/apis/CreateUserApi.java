package com.qa.apis;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateUserApi extends BaseApi{

    public CreateUserApi(APIRequestContext apiRequestContext) {
        super(apiRequestContext);
    }

    public APIResponse createUser(String path, String headerKey, String headerValue, Object body) {
        return super.getApiRequestContext().post(path,
                setHeaderParameter("Content-Type", "application/json")
                        .setHeader(headerKey, "Bearer " + headerValue)
                        .setData(body));
    }


}
