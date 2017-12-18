package com.springboot.testing.service;


import com.springboot.testing.domain.VehicleDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RunWith(SpringRunner.class)
public class VehicleDetailJsonTesting {

    @Autowired
    private JacksonTester<VehicleDetail> json;


    @Test
    public void testSerialize() throws IOException {
        VehicleDetail vehicleDetail = new VehicleDetail(1l, "Honda", "Civic");
        ClassPathResource resource = new ClassPathResource("vehicledetail.json");
        assertThat(this.json.write(vehicleDetail)).isEqualToJson(resource.getInputStream());
        assertThat(this.json.write(vehicleDetail)).hasJsonPathStringValue("@.make");
        assertThat(this.json.write(vehicleDetail)).
                extractingJsonPathStringValue("@.make").isEqualTo("Honda");
    }

    @Test
    public void testDeserialize() throws IOException {
        String content = "{\"id\":1,\"make\":\"Ford\",\"model\":\"Focus\"}";
        assertThat(this.json.parse(content)).isEqualTo(new VehicleDetail(1l, "Ford", "Focus"));
        assertThat(this.json.parseObject(content).getMake()).isEqualTo("Ford");
    }
}
