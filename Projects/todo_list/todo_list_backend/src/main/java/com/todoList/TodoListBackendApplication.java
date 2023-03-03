package com.todoList;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class TodoListBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListBackendApplication.class, args);
    }

}
