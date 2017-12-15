package com.shipping.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @NotNull
    @Min(1)
    private Integer id;

    @NotNull
    @Min(1)
    private Integer unit;
}
