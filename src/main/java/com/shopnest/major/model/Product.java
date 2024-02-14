package com.shopnest.major.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id",referencedColumnName = "category_id")
    private Category category;

    private double price;

    private double weight;

    private  String description;

    private String imageName;//we dont usually save images in databases so we provide only name and give this here  SATATIC FOLDER


}
