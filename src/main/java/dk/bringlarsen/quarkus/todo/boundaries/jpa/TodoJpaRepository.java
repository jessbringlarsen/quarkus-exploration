package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import dk.bringlarsen.quarkus.todo.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class TodoJpaRepository implements dk.bringlarsen.quarkus.todo.TodoRepository {

    private TodoSpringDataRepository repository;

    @Inject
    public TodoJpaRepository(TodoSpringDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public dk.bringlarsen.quarkus.todo.Page findAll(int pageIndex, int pageSize) {
        PageRequest page = PageRequest.of(pageIndex, pageSize);
        Page<TodoEntity> result = repository.findAll(page);

        List<Todo> todos = result.get()
            .map(this::map)
            .collect(Collectors.toList());
        return new dk.bringlarsen.quarkus.todo.Page(result.hasPrevious(), result.hasNext(), todos); 
    }

    @Override
    public Optional<Todo> findById(long id) {
        Optional<TodoEntity> result = repository.findById(id);
        if(!result.isPresent()) {
            return Optional.empty();
        }
        TodoEntity entity = result.get();
        return Optional.of(map(entity));
    }

    @Override
    public Optional<Todo> create(Todo todo) {
        TodoEntity entity = repository.save(new TodoEntity(todo.getTitle(), todo.isCompleted()));
        if(entity.id == null) {
            return Optional.empty();
        }
        return Optional.of(map(entity));
    }

    @Override
    public boolean delete(long id) {
        Optional<TodoEntity> result = repository.findById(id);
        if(!result.isPresent()) {
            return false;
        }
        repository.delete(result.get());
        return true;
    }

    private Todo map(TodoEntity entity) {
        return new Todo(entity.id, entity.title, entity.completed);
    }
}
