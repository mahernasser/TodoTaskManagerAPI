package com.maher.repositories;

import com.maher.enitites.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface TodoRepo extends MongoRepository<Todo, String> {
    Optional<Todo> findTodoByTitle(String title);


    Todo findAllByDescription(String description);


}
