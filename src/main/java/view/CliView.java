package view;

import controller.TaskController;
import model.Task;
import exceptions.*;
import java.time.LocalDate;
import java.util.Scanner;

public class CliView {

    public void showMenu() {
        System.out.println("\n===== TaskFlow Menu =====");
        System.out.println("1. Add Task");
        System.out.println("2. Delete Task");
        System.out.println("3. View Tasks");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    public void run(TaskController controller) {
        Scanner scanner = new Scanner(System.in);
        String username = "user"; // default user for simplicity

        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter priority (1-5): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter deadline (YYYY-MM-DD): ");
                    String dateStr = scanner.nextLine();
                    try {
                        LocalDate deadline = LocalDate.parse(dateStr);
                        Task task = new Task(title, priority, deadline);
                        controller.addTask(username, task);
                        System.out.println("Task added!");
                    } catch (InvalidTaskException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                    }
                    break;

                case 2:
                    System.out.print("Enter task title to delete: ");
                    String delTitle = scanner.nextLine();
                    try {
                        controller.deleteTask(username, delTitle);
                        System.out.println("Task deleted!");
                    } catch (UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        controller.printAllTasks(username);
                    } catch (UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option, try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
