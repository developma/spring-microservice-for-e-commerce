package com.shipping.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @NotNull
    private String zipCode;

    @NotNull
    @Size(min = 10, max = 50)
    private String location;
}
