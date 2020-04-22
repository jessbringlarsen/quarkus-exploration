package dk.bringlarsen.quarkus.todo;

public class Todo {

    private Long id;
    private String title;
    private boolean completed;
    private int version;

    public Todo(String title) {
        this(0L, title, false, 0);
    }

    public Todo(Long id, String title, boolean completed, int version) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getVersion() {
        return version;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
