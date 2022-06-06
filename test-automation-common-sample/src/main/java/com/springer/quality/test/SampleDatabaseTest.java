package com.springer.quality.test;

import com.springer.quality.database.entities.CaItemEntity;
import com.springer.quality.database.repository.SampleRepository;
import org.testng.annotations.Test;

public class SampleDatabaseTest {

    private static final SampleRepository SAMPLE_REPO = new SampleRepository();

    @Test
    public void sampleDbTest() {
        CaItemEntity caItemEntity = SAMPLE_REPO.findItemId("7601444.xml");
        System.out.println("output : " + caItemEntity);
        System.out.println("Item Id : "+caItemEntity.getItemId());
    }
}
