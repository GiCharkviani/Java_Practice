package com.todoList.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todoList.enums.todo.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todos")
public class Todo
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(length = 5000, nullable = false)
    private String whatTodo;
    @Column(nullable = false)
    private LocalDateTime whenTodo;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;


    @Column(nullable = false,
            columnDefinition = "VARCHAR(12) CHECK (status IN ('TO_DO', 'IN_PROGRESS', 'CANCELLED', 'DONE') )")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(
            cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
}
