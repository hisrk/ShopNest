package com.shopnest.major.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
           @Id
            @GeneratedValue(strategy= GenerationType.AUTO)
                   @Column(name="category_id")
             private int id;

          private  String name;

}
