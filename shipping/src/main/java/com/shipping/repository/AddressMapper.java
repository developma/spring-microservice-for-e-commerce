package com.shipping.repository;

import com.shipping.domain.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {

    void insertAddress(Address address);
}
