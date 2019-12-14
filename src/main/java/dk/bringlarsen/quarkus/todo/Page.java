package dk.bringlarsen.quarkus.todo;

import java.util.List;

public class Page {

    private boolean hasPrevious;
    private boolean hasNext;
    private List<Todo> todos;

    public Page(boolean hasPrevious, boolean hasNext, List<Todo> todos) {
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.todos = todos;
    }

    public boolean hasPrevious() {
        return hasPrevious;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public List<Todo> getTodos() {
        return todos;
    }
}