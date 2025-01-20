package repositories;

import conn.Conn;
import domain.Task;
import statements.TaskStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final Conn connection;
    private final TaskStatement taskStatement;

    public TaskRepository(Conn conn, TaskStatement taskStatement) {
        this.connection = conn; this.taskStatement = taskStatement;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = connection.getConnection(); PreparedStatement preparedStatement = taskStatement.selectPreparedStatement(conn)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Task task = new Task(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                task.setDone(rs.getBoolean("done"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks from the database: " + e.getMessage(), e);
        }
        return tasks;
    }

    public void create(Task task) {
        try (Connection conn = connection.getConnection(); PreparedStatement preparedStatement = taskStatement.insertPreparedStatement(conn, task.getName(), task.getDescription())) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error creating task: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        try (Connection conn = connection.getConnection(); PreparedStatement preparedStatement = taskStatement.deletePreparedStatement(conn, id)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting task: " + e.getMessage(), e);
        }
    }

    public void update(Task task, String name, String description) {
        if (name.isEmpty()) {
            name = task.getName();
        }

        if (description.isEmpty()) {
            description = task.getDescription();
        }

        try (Connection conn = connection.getConnection(); PreparedStatement preparedStatement = taskStatement.updatePreparedStatement(conn, name, description, task.getName())) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating task: " + e.getMessage(), e);
        }
    }

    public Task findByName(String name) {
        try (Connection conn = connection.getConnection(); PreparedStatement preparedStatement = taskStatement.selectByNamePreparedStatement(conn, name)) {
            ResultSet rs = preparedStatement.executeQuery();
            Task task = null;
            while (rs.next()) {
                task = new Task(rs.getString("name"), rs.getString("description"));
            }
            return task;
        } catch (Exception e) {
            throw new RuntimeException("Error searching by name: " + e.getMessage(), e);
        }
    }


    public void toggleDoneStatus(Task task) {
        try (Connection conn = connection.getConnection(); PreparedStatement preparedStatement = taskStatement.toggleDoneStatusPreparedStatement(conn, task.getName(), task.getDone())) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating task: " + e.getMessage(), e);
        }
    }

    public void listAllTasks() {
        List<Task> tasks = getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDone()) {
                System.out.printf("%d - %s: \"%s\" > [X]\n", i + 1, task.getName(), task.getDescription());
            } else {
                System.out.printf("%d - %s: \"%s\" > [ ]\n", i + 1, task.getName(), task.getDescription());
            }
        }
    }
}
