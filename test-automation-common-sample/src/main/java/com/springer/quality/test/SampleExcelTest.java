package com.springer.quality.test;

import com.springer.quality.utilities.Property;
import com.springer.quality.utilities.ReadExcel;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class SampleExcelTest {

    @Test(priority = 0)
    public void sampleExcelReadTest() {
        try {
            ReadExcel testObj = ReadExcel.getExcelData(Property.get("testExcelPath"));
            log.info("Data : " + testObj.getSingleData(2, 9, 4));
        } catch (Exception e) {
            log.error("Exception");
            e.printStackTrace();
        }
    }
}
