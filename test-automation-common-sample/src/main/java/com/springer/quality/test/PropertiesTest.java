package com.springer.quality.test;

import com.springer.quality.utilities.Property;
import org.testng.annotations.Test;

public class PropertiesTest {

    @Test
    public void SamplePropertyTest(){
        System.out.println("Testing property");
        System.out.println(Property.get("name"));
        System.out.println("Property end");
    }
}
