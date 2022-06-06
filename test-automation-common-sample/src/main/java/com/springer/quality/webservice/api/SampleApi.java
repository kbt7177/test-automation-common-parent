package com.springer.quality.webservice.api;

import com.springer.quality.api.BaseApi;
import com.springer.quality.api.item.ApiItem;
import com.springer.quality.api.model.ApiConfig;
import com.springer.quality.api.model.ApiUserConfig;
import com.springer.quality.api.response.ListResponse;
import com.springer.quality.webservice.api.endpoints.SampleEndpoint;
import com.springer.quality.webservice.config.MyApiConfig;

import java.util.HashMap;
import java.util.Map;

public class SampleApi extends BaseApi {

    public SampleApi(ApiUserConfig user) {
        super(user);
    }

    public ListResponse<HashMap> getTrials(Map<String, String> queryParams) {
        return ListResponse.wrap(init(getUser())
                .queryParams(queryParams)
                .get(SampleEndpoint.TRIALS.getEndpoint()), HashMap.class);
    }

    @Override
    protected ApiConfig getConfig() {
        return ApiItem.instance().getConfig(MyApiConfig.class).getSampleApi();
    }
}
