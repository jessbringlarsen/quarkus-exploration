package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import dk.bringlarsen.quarkus.todo.Page;
import dk.bringlarsen.quarkus.todo.Todo;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class TodoJpaRepositoryTest {

    @Inject
    TodoJpaRepository repository;

    @Test
    public void testCreate() {
        Optional<Todo> todo = repository.create(new Todo("title 1"));

        assertTrue(todo.isPresent());
        assertEquals("title 1", todo.get().getTitle());
    }

    @Test
    public void testFindAll() {
        repository.create(new Todo("title 1"));
        repository.create(new Todo("title 2"));

        Page page = repository.findAll(0, 1);

        assertTrue(page.hasNext());
        assertFalse(page.hasPrevious());
    }
}