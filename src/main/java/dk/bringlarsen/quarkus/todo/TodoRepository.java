package dk.bringlarsen.quarkus.todo;

import java.util.Collection;
import java.util.Optional;

public interface TodoRepository {

    Collection<Todo> findAll(int pageIndex, int pageSize);

    Optional<Todo> findById(long id);

    Optional<Todo> create(Todo todo);

    boolean delete(long id);
}
