package com.shipping.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {

    private Long id;

    @NotNull
    @Valid
    private List<Item> item;

    @NotNull
    @Size(min = 10, max = 20)
    private String senderName;

    @NotNull
    @Size(min = 10, max = 20)
    private String receiverName;

    @NotNull
    @Valid
    private Address address;

}

