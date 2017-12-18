package com.springboot.testing.service;

import com.springboot.testing.domain.VehicleDetail;

import java.util.Collection;

public interface VehicleDetailsService {
    VehicleDetail save(VehicleDetail vehicleDetail);

    VehicleDetail findById(Long id);

    Collection<VehicleDetail> findAll();
}
