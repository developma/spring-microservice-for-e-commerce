package com.shipping.repository;

import com.shipping.domain.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderHistoryMapper {

    void insertOrderHistory(OrderInfo orderInfo);
}
