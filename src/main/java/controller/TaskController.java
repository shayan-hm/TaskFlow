package controller;

import model.Task;
import model.User;
import exceptions.*;
import database.DatabaseManager;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TaskController {
    private HashMap<String, ArrayList<Task>> database;
    private HashMap<String, User> users;
    private DatabaseManager dbManager;

    public TaskController() {
        this.database = new HashMap<>();
        this.users = new HashMap<>();
        this.dbManager = DatabaseManager.getInstance();
        loadData();
    }

    // add a task for a user
    public synchronized void addTask(String username, Task task) {
        if (!database.containsKey(username)) {
            database.put(username, new ArrayList<>());
            if (!users.containsKey(username)) {
                try {
                    users.put(username, new User(username, "1234"));
                } catch (InvalidTaskException ignored) {
                }
            }
        }
        database.get(username).add(task);
        saveData();
    }

    // delete a task by title for a user
    public synchronized void deleteTask(String username, String taskTitle) throws UserNotFoundException {
        if (!database.containsKey(username)) {
            throw new UserNotFoundException("کاربر یافت نشد: " + username);
        }
        ArrayList<Task> tasks = database.get(username);
        tasks.removeIf(task -> task.getTitle().equals(taskTitle));
        saveData();
    }

    public synchronized void updateTask(String username, String oldTitle, Task updatedTask) throws UserNotFoundException {
        if (!database.containsKey(username)) {
            throw new UserNotFoundException("کاربر یافت نشد: " + username);
        }
        ArrayList<Task> tasks = database.get(username);
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTitle().equals(oldTitle)) {
                tasks.set(i, updatedTask);
                saveData();
                return;
            }
        }
    }

    public synchronized PriorityQueue<Task> getTasksByPriority(String username) throws UserNotFoundException {
        if (!database.containsKey(username)) {
            throw new UserNotFoundException("کاربر یافت نشد: " + username);
        }
        return new PriorityQueue<>(database.get(username));
    }

    public synchronized void loadData() {
        dbManager.loadData(users, database);
    }

    public synchronized void saveData() {
        dbManager.saveData(users, database);
    }

    public synchronized List<Task> getCompletedTasks(String username) throws UserNotFoundException {
        if (!database.containsKey(username)) {
            throw new UserNotFoundException("کاربر یافت نشد: " + username);
        }
        return database.get(username).stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    public synchronized List<Task> getHighPriorityTasks(String username, int minPriority) throws UserNotFoundException {
        if (!database.containsKey(username)) {
            throw new UserNotFoundException("کاربر یافت نشد: " + username);
        }
        return database.get(username).stream()
                .filter(task -> task.getPriority() >= minPriority)
                .collect(Collectors.toList());
    }

    public synchronized void printAllTasks(String username) throws UserNotFoundException {
        if (!database.containsKey(username)) {
            throw new UserNotFoundException("کاربر یافت نشد: " + username);
        }
        database.get(username).stream()
                .forEach(task -> System.out.println("- " + task.getTitle() + " (Priority: " + task.getPriority() + ")"));
    }

    // auto save in background thread every 5 seconds
    public synchronized void startAutoSave() {
        Thread autoSaveThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    saveData();
                    System.out.println("Auto-saved.");
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        autoSaveThread.setDaemon(true);
        autoSaveThread.start();
    }
}
