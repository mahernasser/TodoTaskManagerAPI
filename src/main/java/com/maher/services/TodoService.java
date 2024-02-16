package com.maher.services;

import com.maher.enitites.Todo;
import com.maher.exceptions.ConflictException;
import com.maher.exceptions.NotFoundException;
import com.maher.repositories.TodoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    Logger logger = LoggerFactory.getLogger(TodoService.class);


    public List<Todo> getAllTasks() {
        List<Todo> tasks = todoRepository.findAll();
        logger.info("All tasks are retrieved {}", tasks.size());
        return tasks;
    }

    public Todo findById(String id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Record associated with this id " + id));
    }


    public Todo findByTitle(String title) {

        return todoRepository.findTodoByTitle(title)
                .orElseThrow(() -> new NotFoundException("No Record associated with this title " + title));

    }


    public Todo insert(Todo todo) {
        if (todo == null) {
            throw new IllegalArgumentException("Todo cannot be null");
        }
        if (todoRepository.findTodoByTitle(todo.getTitle()).isPresent()) {
            throw new ConflictException("Todo with title " + todo.getTitle() + " already exists");
        }


        return todoRepository.insert(todo);
    }


    public void deleteById(String id) {
        todoRepository.findById(id).ifPresent(todoRepository::delete);
    }

    public void clearAllTodos() {
        todoRepository.deleteAll();
    }

}
