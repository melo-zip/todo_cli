package services;

import domain.Task;
import repositories.TaskRepository;


public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void create(Task task) {
        taskRepository.create(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task.getId());
    }

    public void update(Task task, String name, String description) {
        taskRepository.update(task, name, description);
    }

    public Task findByName(String name) {
        return taskRepository.findByName(name);
    }

    public void toggleDoneStatus(Task task) {
        task.setDone(!task.getDone());
        taskRepository.toggleDoneStatus(task);
    }

    public Task getTaskByIndex(int index) {
        index--;

        return taskRepository.getTasks().get(index);
    }


    public void listAllTasks() {
        taskRepository.listAllTasks();
    }
}
