package database;

import model.User;
import model.Task;
import exceptions.InvalidTaskException;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class DatabaseManager {

    private static DatabaseManager instance;

    private DatabaseManager() {
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void saveData(HashMap<String, User> users,
                         HashMap<String, ArrayList<Task>> database) {
        try (FileWriter writer = new FileWriter("tasks.txt")) {
            for (String username : users.keySet()) {
                User user = users.get(username);
                writer.write("USER," + username + "," + user.getPassword() + "\n");
                ArrayList<Task> tasks = database.get(username);
                if (tasks != null) {
                    for (Task task : tasks) {
                        writer.write("TASK," + username + "," +
                                task.getTitle() + "," +
                                task.getPriority() + "," +
                                task.getDeadline() + "," +
                                task.isCompleted() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("خطا در ذخیره‌سازی: " + e.getMessage());
        }
    }

    public void loadData(HashMap<String, User> users,
                         HashMap<String, ArrayList<Task>> database) {
        File file = new File("tasks.txt");
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("USER")) {
                    User user = new User(parts[1], parts[2]);
                    users.put(parts[1], user);
                    database.put(parts[1], new ArrayList<>());
                } else if (parts[0].equals("TASK")) {
                    Task task = new Task(
                            parts[2],
                            Integer.parseInt(parts[3]),
                            LocalDate.parse(parts[4])
                    );
                    task.setCompleted(Boolean.parseBoolean(parts[5]));
                    database.get(parts[1]).add(task);
                }
            }
        } catch (IOException | InvalidTaskException e) {
            System.out.println("خطا در بارگذاری: " + e.getMessage());
        }
    }
}
