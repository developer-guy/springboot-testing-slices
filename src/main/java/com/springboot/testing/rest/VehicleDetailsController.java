package com.springboot.testing.rest;

import com.springboot.testing.domain.VehicleDetail;
import com.springboot.testing.service.VehicleDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/vehicles")
public class VehicleDetailsController {

    private VehicleDetailsService vehicleDetailsService;


    public VehicleDetailsController(VehicleDetailsService vehicleDetailsService) {
        this.vehicleDetailsService = vehicleDetailsService;
    }

    @PostMapping
    public VehicleDetail save(@RequestBody VehicleDetail vehicleDetail) {
        return vehicleDetailsService.save(vehicleDetail);
    }

    @GetMapping
    public Collection<VehicleDetail> findAll() {
        return vehicleDetailsService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        VehicleDetail byId = vehicleDetailsService.findById(id);
        if (Objects.isNull(byId)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(byId.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
