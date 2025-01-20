package services;

import domain.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.TaskRepository;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    TaskService service;

    @Mock
    TaskRepository repository;

    Task task;

    @BeforeEach
    public void setUp() {
        task = new Task(1, "test", "test");
    }

    @Test
    public void shouldSearchTasksByIndexWithSuccess(){
        List<Task> tasks = Collections.singletonList(task);
        when(repository.getTasks()).thenReturn(tasks);

        Task result = service.getTaskByIndex(1);
        Assertions.assertEquals(task, result);
        verify(repository).getTasks();
        verifyNoMoreInteractions(repository);
    }


    @Test
    public void shouldSearchTasksByNameWithSuccess() {
        when(service.findByName(task.getName())).thenReturn(task);

        Task taskFound = service.findByName(task.getName());
        Assertions.assertEquals(task, taskFound);
        verify(repository).findByName(task.getName());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldCallRepositoryToCreateTask() {
        service.create(task);

        verify(repository).create(task);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldCallRepositoryToUpdateTask() {
        service.update(task, "test2", "test2");

        verify(repository).update(task, "test2", "test2");
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldCallRepositoryToDeleteTask() {
        service.delete(task);

        verify(repository).delete(task.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldCallRepositoryToToggleDoneStatus(){
        service.toggleDoneStatus(task);

        Assertions.assertTrue(task.getDone());

        verify(repository).toggleDoneStatus(task);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldCallRepositoryToListAllTasks(){
        service.listAllTasks();

        verify(repository).listAllTasks();
        verifyNoMoreInteractions(repository);
    }
}
