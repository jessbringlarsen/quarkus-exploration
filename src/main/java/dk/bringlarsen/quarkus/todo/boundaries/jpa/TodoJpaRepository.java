package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import dk.bringlarsen.quarkus.todo.PageableResult;
import dk.bringlarsen.quarkus.todo.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Scope;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class TodoJpaRepository implements dk.bringlarsen.quarkus.todo.TodoRepository {

    private TodoSpringDataRepository repository;

    @Inject
    public TodoJpaRepository(TodoSpringDataRepository repository) {
        this.repository = repository;
    }

    public void init() {
    }

    @Override
    public PageableResult<Todo> findAll(int pageIndex, int pageSize) {
        PageRequest page = PageRequest.of(pageIndex, pageSize);
        Page<TodoEntity> result = repository.findAll(page);

        List<Todo> todos = result.get()
            .map(this::map)
            .collect(Collectors.toList());
        return new PageableResult(result.hasPrevious(), result.hasNext(), todos);
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
        if(entity.getId() == null) {
            return Optional.empty();
        }
        return Optional.of(map(entity));
    }

    @Override
    public Optional<Todo> save(Todo todo) {
        Optional<TodoEntity> entity = repository.findById(todo.getId());
        if(!entity.isPresent()) {
            return Optional.empty();
        }
        TodoEntity todoEntity = entity.get();
        todoEntity.setTitle(todo.getTitle());
        todoEntity.setCompleted(todo.isCompleted());
        todoEntity = repository.save(todoEntity);
        return Optional.of(map(todoEntity));
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
        return new Todo(entity.getId(), entity.getTitle(), entity.getCompleted(), entity.getVersion());
    }
}
