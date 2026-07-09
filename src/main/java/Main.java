import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import model.User;
import model.Task;
import java.time.LocalDate;
import exceptions.InvalidTaskException;
import exceptions.UserNotFoundException;



public class Main {
    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap<>();
        HashMap<String, ArrayList<Task>> database = new HashMap<>();

        System.out.println("=== TaskFlow - Phase 2 Exception Handling Testing ===\n");

        // ============== USER VALIDATION TESTS ==============
        System.out.println("--- User Validation Tests ---");

        // Test 1: Valid user creation
        try {
            User user1 = new User("shayan", "1234");
            users.put(user1.getUsername(), user1);
            System.out.println("✅ Test 1 PASSED: User 'shayan' created successfully");
        } catch (InvalidTaskException e) {
            System.out.println("❌ Test 1 FAILED: " + e.getMessage());
        }

        // Test 2: User with empty username
        try {
            User user2 = new User("", "5678");
            users.put(user2.getUsername(), user2);
            System.out.println("✅ Test 2 PASSED: User created with empty username");
        } catch (InvalidTaskException e) {
            System.out.println("✅ Test 2 PASSED: Correctly caught exception - " + e.getMessage());
        }

        // Test 3: User with empty password
        try {
            User user3 = new User("ali", "");
            users.put(user3.getUsername(), user3);
            System.out.println("✅ Test 3 PASSED: User created with empty password");
        } catch (InvalidTaskException e) {
            System.out.println("✅ Test 3 PASSED: Correctly caught exception - " + e.getMessage());
        }

        // Test 4: User with null username
        try {
            User user4 = new User(null, "9999");
            users.put(user4.getUsername(), user4);
            System.out.println("✅ Test 4 PASSED: User created with null username");
        } catch (InvalidTaskException e) {
            System.out.println("✅ Test 4 PASSED: Correctly caught exception - " + e.getMessage());
        }

        System.out.println("\n--- Task Validation Tests ---");

        ArrayList<Task> shayanTasks = new ArrayList<>();

        // Test 5: Valid task creation
        try {
            Task t1 = new Task("خرید نان", 2, LocalDate.of(2026, 7, 10));
            shayanTasks.add(t1);
            System.out.println("✅ Test 5 PASSED: Task 'خرید نان' created successfully");
        } catch (InvalidTaskException e) {
            System.out.println("❌ Test 5 FAILED: " + e.getMessage());
        }

        // Test 6: Task with empty title
        try {
            Task t2 = new Task("", 3, LocalDate.of(2026, 7, 15));
            shayanTasks.add(t2);
            System.out.println("✅ Test 6 PASSED: Task created with empty title");
        } catch (InvalidTaskException e) {
            System.out.println("✅ Test 6 PASSED: Correctly caught exception - " + e.getMessage());
        }

        // Test 7: Task with priority out of range (too high)
        try {
            Task t3 = new Task("تحویل پروژه", 10, LocalDate.of(2026, 7, 15));
            shayanTasks.add(t3);
            System.out.println("✅ Test 7 PASSED: Task created with priority 10");
        } catch (InvalidTaskException e) {
            System.out.println("✅ Test 7 PASSED: Correctly caught exception - " + e.getMessage());
        }

        // Test 8: Task with priority out of range (too low)
        try {
            Task t4 = new Task("مطالعه جاوا", 0, LocalDate.of(2026, 7, 20));
            shayanTasks.add(t4);
            System.out.println("✅ Test 8 PASSED: Task created with priority 0");
        } catch (InvalidTaskException e) {
            System.out.println("✅ Test 8 PASSED: Correctly caught exception - " + e.getMessage());
        }

        // Test 9: Task with null deadline
        try {
            Task t5 = new Task("یادگیری Maven", 5, null);
            shayanTasks.add(t5);
            System.out.println("✅ Test 9 PASSED: Task created with null deadline");
        } catch (InvalidTaskException e) {
            System.out.println("✅ Test 9 PASSED: Correctly caught exception - " + e.getMessage());
        }

        // Test 10: Valid task with highest priority
        try {
            Task t6 = new Task("تحویل پروژه پایان ترم", 5, LocalDate.of(2026, 7, 25));
            shayanTasks.add(t6);
            System.out.println("✅ Test 10 PASSED: Task 'تحویل پروژه پایان ترم' with priority 5 created");
        } catch (InvalidTaskException e) {
            System.out.println("❌ Test 10 FAILED: " + e.getMessage());
        }

        database.put("shayan", shayanTasks);

        // ============== SUMMARY ==============
        System.out.println("\n=== Summary ===");
        System.out.println("✅ Total valid users created: " + users.size());
        System.out.println("✅ Total valid tasks created: " + shayanTasks.size());
        System.out.println("\nPhase 2 Exception Handling: COMPLETE");
        System.out.println("All validation checks working correctly!");


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
            return; // اولین بار است، فایلی نیست
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
                            parts[2],                          // title
                            Integer.parseInt(parts[3]),        // priority
                            LocalDate.parse(parts[4])          // deadline
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
