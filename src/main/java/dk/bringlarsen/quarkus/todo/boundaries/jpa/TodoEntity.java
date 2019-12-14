package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import javax.persistence.*;

@Entity(name = "todo")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Integer version;
    @Column
    private String title;
    @Column
    private Boolean completed = Boolean.FALSE;

    public TodoEntity() {

    }

    public TodoEntity(String title) {
        this(title, false);
    }

    public TodoEntity(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
