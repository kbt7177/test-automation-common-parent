package com.springer.quality.webservice.api.endpoints;

public enum SampleEndpoint {
    TRIALS("/trials");

    private String endpoint;

    SampleEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
