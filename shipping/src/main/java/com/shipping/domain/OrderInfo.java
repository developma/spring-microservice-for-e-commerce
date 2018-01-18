package com.shipping.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {

    @NotNull
    private Long registerId;

    @NotNull
    @Valid
    private List<OrderedItem> orderedItem;

    @NotNull
    @Size(min = 10, max = 20)
    private String senderName;

    @NotNull
    @Valid
    private Address address;

}

