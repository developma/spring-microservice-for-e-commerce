package com.inventory.domain;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Integer id;

    private String name;

    private Category category;

}
