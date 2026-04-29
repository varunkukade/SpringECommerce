package com.example.FakeECommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeECommerce.dtos.AddressDTO;
import com.example.FakeECommerce.repositories.AddressRepository;
import com.example.FakeECommerce.schema.Address;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }

    public Address createNewAddress(AddressDTO addressDTO) {
        Address address = Address.builder()
                .longitude(addressDTO.getLongitude())
                .lattitude(addressDTO.getLattitude())
                .description(addressDTO.getDescription())
                .build();
        return addressRepository.save(address);
    }

}
