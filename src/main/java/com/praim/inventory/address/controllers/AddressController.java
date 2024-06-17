package com.praim.inventory.address.controllers;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    private final AddressService addressService;


    @PostMapping
    public ResponseEntity<Void> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        var addressCreated = addressService.save(addressDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(addressCreated.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
