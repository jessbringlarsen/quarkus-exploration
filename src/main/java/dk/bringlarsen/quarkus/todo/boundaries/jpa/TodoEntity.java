package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;
    public boolean completed;

    public TodoEntity() {

    }

    public TodoEntity(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }
}
