package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import dk.bringlarsen.quarkus.todo.PageableResult;
import dk.bringlarsen.quarkus.todo.Todo;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TodoJpaRepositoryTest {

    @Inject
    TodoJpaRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    public void init() {

    }

    @Test
    public void testCreate() {
        Optional<Todo> todo = repository.create(new Todo("title 1"));

        assertTrue(todo.isPresent());
        assertEquals("title 1", todo.get().getTitle());
    }

    @Transactional
    @Test
    public void testUpdate() {
        Todo todo = repository.create(new Todo("title 1")).get();

        todo.setTitle("title 2");
        Todo updatedTodo = repository.save(todo).get();
        clearPersistenceContext();

        updatedTodo = repository.findById(todo.getId()).get();
        assertEquals("title 2", updatedTodo.getTitle());
        assertEquals(todo.getVersion()+1, updatedTodo.getVersion());
    }

    @Test
    public void testFindAll() {
        repository.create(new Todo("title 1"));
        repository.create(new Todo("title 2"));

        PageableResult<Todo> pageableResult = repository.findAll(0, 1);

        assertTrue(pageableResult.hasNext());
        assertFalse(pageableResult.hasPrevious());
    }

    private void clearPersistenceContext() {
        entityManager.flush();
        entityManager.clear();
    }
}