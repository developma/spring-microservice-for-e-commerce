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

    private Integer price;

    private Integer unit;

    private String description;

    // FIXME: I don't know how to handle a big data likes image data.
//    private InputStream pict;
    private String pict;

    private Category category;

    private Long versionno;

}
