package com.todoList.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue
    private int id;
    private String firstname;
    private String lastname;

    @Column(unique = true, nullable = false, length = 1000)
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Todo> todos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Token> tokens;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;
}
