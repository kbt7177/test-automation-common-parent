package com.springer.quality.test;

import com.springer.quality.utilities.Property;
import com.springer.quality.utilities.ReadExcel;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class SampleExcelTest {

    @Test
    public void sampleExcelReadTest(){
        try {
            ReadExcel testObj = ReadExcel.getExcelData(Property.get("testExcelPath"));
            log.info("First Name : " + testObj.getSingleData(1,1));
            log.info("Last Name : " + testObj.getSingleData(1,2));
            log.info("id Name : " + testObj.getSingleData(2,1,1));
            log.info("company Name : " + testObj.getSingleData("EmployeeInfo",1,2));
            ReadExcel newObj = ReadExcel.getExcelData(Property.get("newExcelPath"));
            log.info("company Name : " + newObj.getSingleData(1,1,1));
            log.info("location Name : " + newObj.getSingleData(1,1,2));
            log.info("Wrong sheet name Name : " + newObj.getSingleData("abc",1,2));

        }catch (Exception e){
            log.error("Exception");
            e.printStackTrace();
        }
    }
}
