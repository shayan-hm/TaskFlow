import java.util.HashMap;
import java.util.ArrayList;
import model.User;
import model.Task;
import database.DatabaseManager;
import exceptions.InvalidTaskException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        HashMap<String, User> users = new HashMap<>();
        HashMap<String, ArrayList<Task>> database = new HashMap<>();

        DatabaseManager db = DatabaseManager.getInstance();
        db.loadData(users, database);


            User shayan = new User("shayan", "1234");
            users.put(shayan.getUsername(), shayan);
            database.put(shayan.getUsername(), new ArrayList<>());

            database.get("shayan").add(new Task("خرید نان", 2, LocalDate.of(2026, 7, 10)));
            database.get("shayan").add(new Task("تحویل پروژه", 5, LocalDate.of(2026, 7, 25)));
            database.get("shayan").add(new Task("مطالعه جاوا", 3, LocalDate.of(2026, 8, 1)));


        db.saveData(users, database);

        
        for (String username : database.keySet()) {
            System.out.println("user: " + username);
            for (Task t : database.get(username)) {
                System.out.println("  - " + t.getTitle() + " | priority: " + t.getPriority() + " | done: " + t.isCompleted());
            }
        }
    }
}