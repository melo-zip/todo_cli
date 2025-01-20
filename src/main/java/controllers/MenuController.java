package controllers;

import conn.Conn;
import repositories.TaskRepository;
import services.MenuService;
import services.TaskService;
import statements.TaskStatement;

public class MenuController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final MenuService menuService;

    public MenuController() {
        Conn conn = new Conn();
        TaskStatement taskStatement = new TaskStatement();
        this.taskRepository = new TaskRepository(conn, taskStatement);
        this.taskService = new TaskService(taskRepository);
        this.menuService = new MenuService();
    }

    public static void main(String[] args) {
        MenuController menuController = new MenuController();

        menuController.taskService.listAllTasks();
        while (true) {
            int option = menuController.menuService.listMenuOptions();
            switch (option) {
                case 1:
                    menuController.menuService.createTask();
                    break;
                case 2:
                    menuController.menuService.updateTask();
                    break;
                case 3:
                    menuController.menuService.toggleDoneStatus();
                    break;
                case 4:
                    menuController.menuService.removeTask();
                    break;
                case 5:
                    System.out.println("End!");
                    break;
                default:
                    System.out.println(option + " is not a valid option, please choose another one");
                    continue;
            }
            menuController.taskService.listAllTasks();
            break;
        }

    }
}
