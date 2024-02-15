package com.maher.enitites;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tasks")
public class Todo {
    @Id
    private String id;

    @NotNull
    @Size(min = 3,message = "Title must me at least 3 letters")
    private String title;
    @NotNull(message = "Description Is Required to save the task")
    private String description;

    private long timestamp;

    Todo() {
        this.timestamp = System.currentTimeMillis();
    }

    public Todo(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timestamp= System.currentTimeMillis();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
