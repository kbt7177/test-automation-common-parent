package com.springer.quality.api.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public abstract class ApiResponse<T> {
    private Response response;
    private T body;
    private Map<String, Object> errors;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public T getBody() {
        return body;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public <T> T getErrors(Class<T> cls) {
        return new ObjectMapper().convertValue(errors, cls);
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void setErrors(Response response) {
        try {
            this.errors = response.as(Map.class);
        } catch (Exception e) {
            log.debug("Could not deserialize body to: " + Map.class, e);
        }
    }

    protected void setBody(Response response, Class<T> type) {
        try {
            log.info("Converting Json to : " + type.getSimpleName());
            this.body = response.as(type);
        } catch (Exception e) {
            log.warn("Could not deserialize body to: " + type, e);
            setErrors(response);
        }
    }

    protected void setBody(Response response, TypeRef<T> typeRef) {
        try {
            log.info("Converting JSON to : ", typeRef.getType().getTypeName());
            this.body = response.as(typeRef);
        } catch (Exception e) {
            log.warn("Could not deserialize body to : ", typeRef, e);
            setErrors(response);
        }
    }

}
