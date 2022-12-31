package pl.wsb.students.intruductionapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.wsb.students.intruductionapp.model.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    @Query("select t from Task t where t.done = ?1")
    List<Task> find_todo(String done);
    @Query("select t from Task t where t.done = ?1")
    List<Task> find_done(String done);
    @Query("select t from Task t where t.done like ?1")
    List<Task> findTodo(String done);
    @Query("select t from Task t where t.done like ?1")
    List<Task> findDone(String done);
    @Query("select a from Task a where a.termin like concat(?1, '%') and a.termin like concat('%', ?2)")
    List<Task> findAfter(String termin, String termin1);
    @Query("select a from Task a where a.termin like ?1")
    Iterable<Task> find_today(String termin);
}
