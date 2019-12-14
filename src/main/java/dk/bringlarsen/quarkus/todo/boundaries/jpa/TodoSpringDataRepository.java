package dk.bringlarsen.quarkus.todo.boundaries.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoSpringDataRepository extends JpaRepository<TodoEntity, Long> {
}
