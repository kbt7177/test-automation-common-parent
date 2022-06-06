package com.springer.quality.api.response;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ListResponse<T> extends ApiResponse<List<T>> {

    public static <T> ListResponse<T> wrap(Response response, Class<T> type) {
        ListResponse<T> listResponse = new ListResponse<>();
        listResponse.setResponse(response);
        try {
            listResponse.setBody(response.jsonPath().getList(".", type));
        } catch (ClassCastException e) {
            log.warn("Expected a JSON list but found Object",e);
            listResponse.setErrors(response);
        }
        return listResponse;
    }
}
