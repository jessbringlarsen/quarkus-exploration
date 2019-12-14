package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import dk.bringlarsen.quarkus.todo.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class TodoJpaRepository implements dk.bringlarsen.quarkus.todo.TodoRepository {

    private TodoSpringDataRepository repository;

    @Inject
    public TodoJpaRepository(TodoSpringDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Todo> findAll(int pageIndex, int pageSize) {
        PageRequest page = PageRequest.of(pageIndex, pageSize);
        Page<TodoEntity> result = repository.findAll(page);
        return result.get()
                .map(t -> new Todo(t.id, t.title, t.completed))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Todo> findById(long id) {
        Optional<TodoEntity> result = repository.findById(id);
        if(!result.isPresent()) {
            return Optional.empty();
        }
        TodoEntity entity = result.get();
        return Optional.of(new Todo(entity.id, entity.title, entity.completed));
    }

    @Override
    public Optional<Todo> create(Todo todo) {
        TodoEntity entity = repository.save(new TodoEntity(todo.getTitle(), todo.isCompleted()));
        if(entity == null) {
            return Optional.empty();
        }
        return Optional.of(new Todo(entity.id, entity.title, entity.completed));
    }
}
