package com.shipping.repository;

import com.shipping.domain.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderedItemMapper {
    void insertOrderedItem(Item  item);
}
