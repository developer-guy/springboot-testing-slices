package com.springboot.testing.service;


import com.springboot.testing.domain.VehicleDetail;
import com.springboot.testing.repository.VehicleDetailsRepository;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VehicleDetailServiceTest {

    @TestConfiguration
    static class TestServiceConfiguration {

        @Bean
        public VehicleDetailsService vehicleDetailsService(VehicleDetailsRepository vehicleDetailsRepository) {
            return new VehicleDetailsServiceImpl(vehicleDetailsRepository);
        }
    }

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private VehicleDetailsService vehicleDetailsService;


    @Test
    public void is_saved_succesful() {
        VehicleDetail savedVehicleDetail = this.vehicleDetailsService.save(new VehicleDetail(null, "Ford", "Focus"));
        assertThat(savedVehicleDetail.getId(),is(notNullValue()));
    }


    @Test
    public void is_find_by_id_succesful() {
        VehicleDetail vehicleDetailBePersisted = this.testEntityManager.persist(new VehicleDetail(null, "Ford", "Focus"));
        assertThat(this.vehicleDetailsService.findById(vehicleDetailBePersisted.getId()), is(notNullValue()));
    }

    @Test
    public void is_findAll_successful() {
        this.testEntityManager.persist(new VehicleDetail(null, "Ford", "Focus"));
        this.testEntityManager.persist(new VehicleDetail(null, "Ford", "Transit"));
        this.testEntityManager.persist(new VehicleDetail(null, "Ford", "Kuqa"));

        assertThat(this.vehicleDetailsService.findAll(), hasSize(3));
    }

}
