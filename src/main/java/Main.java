import java.util.HashMap;
import java.util.ArrayList;
import model.User;
import model.Task;
import java.time.LocalDate;
import exceptions.InvalidTaskException;

public class Main {
    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap<>();
        HashMap<String, ArrayList<Task>> database = new HashMap<>();

        User user1 = new User("shayan", "1234");
        users.put(user1.getUsername(), user1);

        ArrayList<Task> shayanTasks = new ArrayList<>();


        try {
            Task t1 = new Task("خرید نان", 2, LocalDate.of(2026, 7, 10));
            shayanTasks.add(t1);
            System.out.println("تسک 1 ساختیم");
        } catch (InvalidTaskException e) {
            System.out.println("خطا در تسک 1: " + e.getMessage());
        }


        try {
            Task t2 = new Task("", 3, LocalDate.of(2026, 7, 15));
            shayanTasks.add(t2);
            System.out.println("تسک 2 ساختیم");
        } catch (InvalidTaskException e) {
            System.out.println("خطا در تسک 2: " + e.getMessage());
        }


        try {
            Task t3 = new Task("تحویل پروژه", 10, LocalDate.of(2026, 7, 15));
            shayanTasks.add(t3);
            System.out.println("تسک 3 ساختیم" );
        } catch (InvalidTaskException e) {
            System.out.println(" خطا در تسک 3: " + e.getMessage());
        }

        database.put(user1.getUsername(), shayanTasks);
    }
}
