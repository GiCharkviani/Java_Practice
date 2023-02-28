package com.todoList.entities;

import com.todoList.entities.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name="tokens")
public class Token {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true, length = 1000)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    public User user;
}