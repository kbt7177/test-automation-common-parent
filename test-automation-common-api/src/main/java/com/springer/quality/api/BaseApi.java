package com.springer.quality.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springer.quality.api.logging.RequestFilter;
import com.springer.quality.api.model.ApiConfig;
import com.springer.quality.api.model.ApiUserConfig;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {
    private static final String USER_HEADER_KEY = null;
    private static final String CUSTOMER_HEADER_KEY = null;
    private static final String CORRELATION_HEADER_KEY = null;

    private final ApiUserConfig user;

    public BaseApi(ApiUserConfig user) {
        this.user = user;
    }

    public ApiUserConfig getUser() {
        return user;
    }

    protected abstract ApiConfig getConfig();

    protected RequestSpecification init(ApiUserConfig user) {
        RestAssuredConfig config = new RestAssuredConfig().objectMapperConfig(
                ObjectMapperConfig.objectMapperConfig().
                        jackson2ObjectMapperFactory((clazz, charset) -> new ObjectMapper().
                                registerModule(new JavaTimeModule()).
                                disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)));
        RequestSpecification spec = RestAssured.given()
                .baseUri(getConfig().getBaseUrl())
                .accept(ContentType.JSON)
                .contentType("application/json")
                .filter(new RequestFilter())
                .config(config);

//        spec.header(CORRELATION_HEADER_KEY, "test_" + UUID.randomUUID());
//        if (user == null) {
//            return spec;
//        }
//        if (user.getUser() != null) {
//            spec.header(USER_HEADER_KEY, user.getUser());
//        }
//        if (user.getCustomer() != null) {
//            spec.header(CUSTOMER_HEADER_KEY, user.getCustomer());
//        }
        return spec;

    }

}
