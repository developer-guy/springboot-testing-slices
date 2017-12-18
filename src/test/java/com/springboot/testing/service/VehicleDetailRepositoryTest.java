package com.springboot.testing.service;


import com.springboot.testing.domain.VehicleDetail;
import com.springboot.testing.repository.VehicleDetailsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VehicleDetailRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private VehicleDetailsRepository vehicleDetailsRepository;

    @Test
    public void is_saved_succesful() {
        VehicleDetail savedVehicleDetail = this.vehicleDetailsRepository.save(new VehicleDetail(null, "Ford", "Focus"));
        assertThat(savedVehicleDetail.getId(),is(notNullValue()));
    }


    @Test
    public void is_find_by_id_succesful() {
        VehicleDetail vehicleDetailBePersisted = this.testEntityManager.persist(new VehicleDetail(null, "Ford", "Focus"));
        assertThat(this.vehicleDetailsRepository.findById(vehicleDetailBePersisted.getId()), is(notNullValue()));
    }

    @Test
    public void is_findAll_successful() {
        this.testEntityManager.persist(new VehicleDetail(null, "Ford", "Focus"));
        this.testEntityManager.persist(new VehicleDetail(null, "Ford", "Transit"));
        this.testEntityManager.persist(new VehicleDetail(null, "Ford", "Kuqa"));

        assertThat(this.vehicleDetailsRepository.findAll(), hasSize(3));
    }
}
