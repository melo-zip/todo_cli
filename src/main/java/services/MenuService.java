package services;

import conn.Conn;
import domain.Task;
import repositories.TaskRepository;
import statements.TaskStatement;

import java.util.Scanner;

public class MenuService {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public MenuService() {
        Conn conn = new Conn();
        TaskStatement taskStatement = new TaskStatement();
        this.taskRepository = new TaskRepository(conn, taskStatement);
        this.taskService = new TaskService(taskRepository);
    }

    public int listMenuOptions() {
        System.out.println("\nChoose one of the options below:");
        System.out.println("1 - Create new task\n2 - Edit task\n3 - Mark task as done/undone\n4 - Remove task\n5 - Exit");
        return Integer.parseInt(scanner.nextLine());
    }

    public void createTask() {
        String name;
        String description;

        while (true) {
            System.out.println("Type the task name: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty");
                continue;
            }
            if (taskService.findByName(name) != null) {
                System.out.println("Name already exists, please type another one");
                continue;
            }
            System.out.println("Type the task description: ");
            description = scanner.nextLine();
            break;
        }
        taskService.create(new Task(name, description));
    }

    public void updateTask() {
        String newName;
        String newDescription;

        while (true) {
            System.out.println("Choose the task you want to edit: ");
            taskService.listAllTasks();
            int option = Integer.parseInt(scanner.nextLine());
            Task taskToUpdate = taskService.getTaskByIndex(option);
            System.out.println(taskToUpdate);
            System.out.println("Type the new name (Just type enter to keep the same): ");
            newName = scanner.nextLine();
            if (newName.isEmpty()) {
                newName = taskToUpdate.getName();
            }
            System.out.println("Type the new description (Just type enter to keep the same): ");
            newDescription = scanner.nextLine();
            if (newDescription.isEmpty()) {
                newDescription = taskToUpdate.getDescription();
            }
            taskService.update(taskToUpdate, newName, newDescription);
            break;
        }
    }

    public void toggleDoneStatus() {
        taskService.listAllTasks();
        System.out.println("Choose which task you want to change status");
        int option = Integer.parseInt(scanner.nextLine());
        Task task = taskService.getTaskByIndex(option);
        taskService.toggleDoneStatus(task);
    }

    public void removeTask() {
        taskService.listAllTasks();
        System.out.println("Choose which task you want to remove");
        int option = Integer.parseInt(scanner.nextLine());
        Task task = taskService.getTaskByIndex(option);
        taskService.delete(task);
    }
}
