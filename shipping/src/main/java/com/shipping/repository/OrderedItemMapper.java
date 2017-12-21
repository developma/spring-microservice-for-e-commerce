package com.shipping.repository;

import com.shipping.domain.OrderedItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderedItemMapper {
    void insertOrderedItem(OrderedItem orderedItem);
}
