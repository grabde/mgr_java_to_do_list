package pl.wsb.students.intruductionapp.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.wsb.students.intruductionapp.model.Task;
import pl.wsb.students.intruductionapp.repository.TaskRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<Task> listAll(String sortDirection)
    {
        return taskRepository.findAll(getSortByDirection(sortDirection));
    }

    public void save (Task task)
    {
        if(task.getModified()==null)
        {
            task.setModified(new Date());
            task.setDone("Nie");
        }

        taskRepository.save(task);
    }
    public Task find(Integer id)
    {
        return taskRepository.findById(id).orElse(null);
    }
    public void delete(Integer id)
    {
        taskRepository.deleteById(id);
    }
    public Iterable<Task> listToday(String sortDirection)
    {
        LocalDate v_ldate = java.time.LocalDate.now();
        String v_sldate = v_ldate.toString();
        Iterable<Task> tasks = taskRepository.find_today(v_sldate, getSortByDirection(sortDirection));
        return tasks;
    }
    public Iterable<Task> listDone(String sortDirection)
    {
        return taskRepository.findDone(getSortByDirection(sortDirection));
    }

    public Iterable<Task> listTodo(String sortDirection)
    {
        return taskRepository.findToDo(getSortByDirection(sortDirection));
    }

    public Iterable<Task> sortAsc()
    {
        Iterable<Task> tasks = taskRepository.sortAsc();
        return tasks;
    }
    public void done(Integer id)
    {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null)
        {
            task.setDone("Tak");
        }
    }
    public void todo(Integer id)
    {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null)
        {
            task.setDone("Nie");
        }
    }

    private static Sort getSortByDirection(String sortDirection) {
        Sort.Direction direction = "DESC".equals(sortDirection)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        return Sort.by(direction, "termin");
    }
}
