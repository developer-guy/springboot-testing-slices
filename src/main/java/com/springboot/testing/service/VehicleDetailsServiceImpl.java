package com.springboot.testing.service;

import com.springboot.testing.domain.VehicleDetail;
import com.springboot.testing.repository.VehicleDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class VehicleDetailsServiceImpl implements VehicleDetailsService {
    private VehicleDetailsRepository vehicleDetailsRepository;

    public VehicleDetailsServiceImpl(VehicleDetailsRepository vehicleDetailsRepository) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
    }

    @Override
    public VehicleDetail save(VehicleDetail vehicleDetail) {
        return vehicleDetailsRepository.save(vehicleDetail);
    }


    @Override
    public VehicleDetail findById(Long id) {
        return vehicleDetailsRepository.findById(id);
    }

    @Override
    public Collection<VehicleDetail> findAll() {
        return vehicleDetailsRepository.findAll();
    }


}
