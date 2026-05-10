package com.example.FakeECommerce.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FakeECommerce.dtos.AddressDTO;
import com.example.FakeECommerce.schema.Address;
import com.example.FakeECommerce.services.AddressService;
import com.example.FakeECommerce.utils.ApiResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {
    private AddressService addressService;

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<Address>>> getAllAddresses(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Address> addresses = addressService.getAllAddresses(pageable);
        return ResponseEntity
                .ok(ApiResponse.<Page<Address>>success(addresses, "Addresses fetched successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity
                .ok(ApiResponse.<Void>success(null, "Address deleted successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Address>> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity
                .ok(ApiResponse.<Address>success(address, "Address fetched successfully"));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Address>> createNewAddress(@RequestBody AddressDTO addressDTO) {
        Address address = addressService.createNewAddress(addressDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<Address>success(address, "Address created successfully"));
    }
}
