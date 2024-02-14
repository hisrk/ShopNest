package com.shopnest.major.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity   //DIKKAT KARTE HAI
@Data
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @NotEmpty//IT IS COMIMG FROM VALIDATION DEPENDENCY THAT WE HAVE ADDED IN POMXML
    @Column(nullable = false)
    private String firstName;

    private String lastName;


    @NotEmpty
    @Column(nullable = false, unique = true)
    @Email(message="{errors.invalid_email}")//VALIDATING EMAIL WITH CORRECT SYNTAX
    private String email;


    private String password;


    @ManyToMany(cascade = CascadeType.MERGE,fetch=FetchType.EAGER)//CascadeType.ALL is an annotation attribute that specifies that all
    // operations should be cascaded from the parent entity to the associated entities.
    //merge() is a method provided by the EntityManager interface that merges the state of
    // a detached entity into the current persistence context
            @JoinTable(name="user_role",joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName
                    = "ID"),
                    inverseJoinColumns = @JoinColumn(name = "ROLE_ID",
                            referencedColumnName = "ID"))
    List<Role> roles;

    public User(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public User() {
    }
}
