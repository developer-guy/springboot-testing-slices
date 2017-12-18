package com.springboot.testing.repository;

import com.springboot.testing.domain.VehicleDetail;
import org.springframework.data.repository.Repository;

import java.util.Collection;

@org.springframework.stereotype.Repository
public interface VehicleDetailsRepository extends Repository<VehicleDetail, Long> {
    VehicleDetail save(VehicleDetail vehicleDetail);

    VehicleDetail findById(Long id);

    Collection<VehicleDetail> findAll();
}
