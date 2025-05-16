package com.qa.apis;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateUserApi extends BaseApi {

    public UpdateUserApi(APIRequestContext apiRequestContext) {
        super(apiRequestContext);
    }

    public APIResponse updateUser(String path, String headerKey, String headerValue, Object body) {
        return super.getApiRequestContext().put(path,
                setHeaderParameter("Content-Type", "application/json")
                        .setHeader(headerKey, "Bearer " + headerValue)
                        .setData(body));
    }
}