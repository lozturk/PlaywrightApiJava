package com.qa.apis;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetUserApi extends BaseApi{



    public GetUserApi(APIRequestContext apiRequestContext) {
        super(apiRequestContext);
    }

    public APIResponse getUsers(String path) {
        return super.getApiRequestContext().get(path);
    }

    public APIResponse getUserById(String path, int id) {
        return super.getApiRequestContext()
                    .get(path, setQueryParameter("id", id));
    }


}
