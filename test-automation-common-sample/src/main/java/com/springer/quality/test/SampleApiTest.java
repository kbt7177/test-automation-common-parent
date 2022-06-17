package com.springer.quality.test;

import com.springer.quality.api.item.ApiUserItem;
import com.springer.quality.api.response.ListResponse;
import com.springer.quality.webservice.api.SampleApi;
import com.springer.quality.webservice.config.MyApiUserConfig;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SampleApiTest {

    private static final MyApiUserConfig user = ApiUserItem.instance().getConfig(MyApiUserConfig.class);


    @Test
    public void sampleApiTest() {

        SampleApi api = new SampleApi(user.getTrialUser());
        Map<String, String> queryParams= new HashMap<>();
        queryParams.put("itemId","785055000087");
        queryParams.put("contentId","7850555");

        ListResponse<HashMap> response = api.getTrials(queryParams);
        log.info("Response : " + response.getResponse().asPrettyString());
    }
}
