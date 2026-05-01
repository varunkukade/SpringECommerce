package com.example.FakeECommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeECommerce.dtos.AddressDTO;
import com.example.FakeECommerce.exception.ResourceNotFoundException;
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
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    public void deleteAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        addressRepository.delete(address);
    }

    public Address createNewAddress(AddressDTO addressDTO) {
        Address address = Address.builder()
                .longitude(addressDTO.getLongitude())
                .lattitude(addressDTO.getLattitude())
                .description(addressDTO.getDescription())
                .build();
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        if (addressDTO.getDescription() != null) {
            address.setDescription(addressDTO.getDescription());
        }
        if (addressDTO.getLattitude() != null) {
            address.setLattitude(addressDTO.getLattitude());
        }
        if (addressDTO.getLongitude() != null) {
            address.setLongitude(addressDTO.getLongitude());
        }
        return addressRepository.save(address);
    }

}
