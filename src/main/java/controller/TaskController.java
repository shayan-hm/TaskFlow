package controller;

import model.Task;
import model.User;
import exceptions.*;
import database.DatabaseManager;
import java.util.*;

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

    public void addTask(String username, Task task) {
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

    public void deleteTask(String username, String taskTitle) throws UserNotFoundException {
        if (!database.containsKey(username)) {
            throw new UserNotFoundException("کاربر یافت نشد: " + username);
        }
        ArrayList<Task> tasks = database.get(username);
        tasks.removeIf(task -> task.getTitle().equals(taskTitle));
        saveData();
    }

    public void updateTask(String username, String oldTitle, Task updatedTask) throws UserNotFoundException {
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

    public PriorityQueue<Task> getTasksByPriority(String username) throws UserNotFoundException {
        if (!database.containsKey(username)) {
            throw new UserNotFoundException("کاربر یافت نشد: " + username);
        }
        return new PriorityQueue<>(database.get(username));
    }

    public void loadData() {
        dbManager.loadData(users, database);
    }

    public void saveData() {
        dbManager.saveData(users, database);
    }
}
