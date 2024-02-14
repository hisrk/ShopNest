package com.shopnest.major.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long id;
    private String name;

    private int categoryId;//chaninging this beacause we are ddealing with category id not with the object

    private double price;

    private double weight;

    private  String description;

    private String imageName;
}
