package com.maher.controllers;

import com.maher.enitites.Todo;
import com.maher.services.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/todos")
public class TodoController {

    private final TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Todo> todo(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Todo>> listTodo() {
        return new ResponseEntity<>(service.getAllTasks(), HttpStatus.OK);
    }


    @PostMapping(value = {"", "/"})
    public ResponseEntity<Todo> addTask(@Valid @RequestBody Todo todo) {
        return new ResponseEntity<Todo>(service.insert(todo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Todo> deleteTask(@PathVariable String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Todo> clearAll() {
        service.clearAllTodos();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/title/{title}")
    public ResponseEntity<Todo> todoByTitle(@PathVariable String title) {
        return new ResponseEntity<>(service.findByTitle(title), HttpStatus.OK);
    }


}
