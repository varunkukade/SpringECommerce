package com.example.FakeECommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FakeECommerce.schema.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
