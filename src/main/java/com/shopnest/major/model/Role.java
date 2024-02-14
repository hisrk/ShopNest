package com.shopnest.major.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Setter
    @Getter
    @Entity
    @Table(name = "roles")
    public class Role {
        //why setter and getter is only getting provided in
        //somethime lambok  @Data results to some complexity
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(length = 60)
        private String name;

        @ManyToMany(mappedBy="roles")
        private List<User> users;
    }

