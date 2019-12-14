package dk.bringlarsen.quarkus.todo;

import java.util.List;

public class PageableResult<T> {

    private boolean hasPrevious;
    private boolean hasNext;
    private List<T> result;

    public PageableResult(boolean hasPrevious, boolean hasNext, List<T> result) {
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.result = result;
    }

    public boolean hasPrevious() {
        return hasPrevious;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public List<T> getResult() {
        return result;
    }
}