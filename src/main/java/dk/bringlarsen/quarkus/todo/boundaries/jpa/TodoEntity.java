package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "todo")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;
    public Boolean completed = Boolean.FALSE;

    public TodoEntity() {

    }

    public TodoEntity(String title) {
        this(title, false);
    }

    public TodoEntity(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }
}
