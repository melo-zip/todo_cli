package repositories;

import conn.Conn;
import domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statements.TaskStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskRepositoryTest {

    private TaskRepository repository;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private TaskStatement mockTaskStatement;
    private Conn mockConn;

    Task task;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConn = mock(Conn.class);
        mockTaskStatement = mock(TaskStatement.class);
        repository = new TaskRepository(mockConn, mockTaskStatement);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        this.task = new Task(1, "test", "test");

        when(mockConn.getConnection()).thenReturn(mockConnection);
    }

    @Test
    public void shouldGetAllTasksWithSuccess() throws SQLException {
        when(mockTaskStatement.selectPreparedStatement(mockConnection)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Task name");
        when(mockResultSet.getString("description")).thenReturn("Task description");
        when(mockResultSet.getBoolean("done")).thenReturn(false);

        List<Task> tasks = repository.getTasks();


        assertNotNull(tasks);
        assertEquals(1, tasks.size());

        Task task1 = tasks.get(0);
        assertEquals(1, task1.getId());
        assertEquals("Task name", task1.getName());
        assertEquals("Task description", task1.getDescription());
        assertFalse(task1.getDone());

        verify(mockResultSet, times(2)).next();
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void shouldThrowRuntimeExceptionGetTasks() throws SQLException{
        when(mockTaskStatement.selectPreparedStatement(mockConnection)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("simulated sqlexception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repository.getTasks());

        assertEquals("Error retrieving tasks from the database: simulated sqlexception", exception.getMessage());
    }

    @Test
    public void shouldCreateTaskWithSuccess() throws SQLException {
        when(mockTaskStatement.insertPreparedStatement(mockConnection, task.getName(), task.getDescription())).thenReturn(mockPreparedStatement);

        repository.create(task);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void shouldThrowRuntimeExceptionCreateTask() throws SQLException{
        when(mockTaskStatement.insertPreparedStatement(mockConnection, task.getName(), task.getDescription())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("simulated sqlexception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repository.create(task));

        assertEquals("Error creating task: simulated sqlexception", exception.getMessage());
    }

    @Test
    public void shouldDeleteTaskWithSuccess() throws SQLException {
        when(mockTaskStatement.deletePreparedStatement(mockConnection, 1)).thenReturn(mockPreparedStatement);

        repository.delete(1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void shouldThrowRuntimeExceptionDeleteTask() throws SQLException{
        when(mockTaskStatement.deletePreparedStatement(mockConnection, task.getId())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("simulated sqlexception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repository.delete(task.getId()));

        assertEquals("Error deleting task: simulated sqlexception", exception.getMessage());
    }

    @Test
    public void shouldUpdateTaskWithSuccess() throws SQLException {
        when(mockTaskStatement.updatePreparedStatement(mockConnection, "test1", "test1", task.getName())).thenReturn(mockPreparedStatement);

        repository.update(task, "test1", "test1");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void shouldThrowRuntimeExceptionUpdateTask() throws SQLException{
        when(mockTaskStatement.updatePreparedStatement(mockConnection, task.getName(), task.getDescription(), task.getName())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("simulated sqlexception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repository.update(task, task.getName(), task.getDescription()));
        assertEquals("Error updating task: simulated sqlexception", exception.getMessage());
    }

    @Test
    public void shouldFindTaskByNameWithSuccess() throws SQLException {
        when(mockTaskStatement.selectByNamePreparedStatement(mockConnection, task.getName())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("name")).thenReturn("test");
        when(mockResultSet.getString("description")).thenReturn("test");

        Task task1 = repository.findByName("test");

        assertEquals(task1.getName(), task.getName());
        assertEquals(task1.getDescription(), task.getDescription());
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void shouldThrowRuntimeExceptionFindTaskByName() throws SQLException{
        when(mockTaskStatement.selectByNamePreparedStatement(mockConnection, task.getName())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("simulated sqlexception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repository.findByName(task.getName()));
        assertEquals("Error searching by name: simulated sqlexception", exception.getMessage());
    }

    @Test
    public void shouldToggleDoneStatusWithSuccess() throws SQLException {
        when(mockTaskStatement.toggleDoneStatusPreparedStatement(mockConnection, task.getName(), task.getDone())).thenReturn(mockPreparedStatement);

        repository.toggleDoneStatus(task);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void shouldThrowRuntimeExceptionToggleDoneStatus() throws SQLException {
        when(mockTaskStatement.toggleDoneStatusPreparedStatement(mockConnection, task.getName(), task.getDone())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("simulated sqlexception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repository.toggleDoneStatus(task));
        assertEquals("Error updating task: simulated sqlexception", exception.getMessage());
    }
}
