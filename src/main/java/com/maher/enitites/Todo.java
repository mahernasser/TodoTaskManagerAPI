package com.maher.enitites;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "tasks")
public class Todo {
    @Id
    private String id;

    @NotNull(message = "Title Is Required to save the task")
    @Size(min = 3, message = "Title must me at least 3 letters")
    private String title;
    @NotNull(message = "Description Is Required to save the task")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy : HH:mm:ss")
    private Date timestamp;

    Todo() {
        this.timestamp = new Date();
    }

    public Todo(String id, String title, String description) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
    }


}