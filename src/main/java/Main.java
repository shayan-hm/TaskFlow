import java.util.HashMap;
import java.util.ArrayList;
import model.User;
import model.Task;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap<>();
        HashMap<String, ArrayList<Task>> database = new HashMap<>();


        User user1 = new User("shayan", "1234");
        users.put(user1.getUsername(), user1);


        ArrayList<Task> shayanTasks = new ArrayList<>();


        Task t1 = new Task("خرید نان", 2, LocalDate.of(2026, 7, 10));
        shayanTasks.add(t1);
        Task t2 = new Task("تحویل پروژه",  1, LocalDate.of(2026, 7, 15) );
        shayanTasks.add(t2);

        database.put(user1.getUsername(), shayanTasks);


        User user2 = new User("parsa", "1234");

        users.put(user2.getUsername(), user2);
        ArrayList<Task> parsaTasks = new ArrayList<>();

        Task k1 = new Task("خرید نان برای شایان", 2, LocalDate.of(2026, 7, 10));
        parsaTasks.add(k1);
        Task k2 = new Task("کمک به شایان برای تحویل پروژه",  1, LocalDate.of(2026, 7, 15) );
        parsaTasks.add(k2);
        database.put(user2.getUsername(), parsaTasks);



        System.out.println(user1.getUsername());
        for (Task t : database.get(user1.getUsername())) {
            System.out.println(t.getTitle() + t.getPriority() +  t.getDeadline()  + t.isCompleted());
        }
        System.out.println("وظایف کاربر " + user2.getUsername() + ":");
        for (Task t : database.get(user2.getUsername())) {
            System.out.println(t.getTitle() + t.getPriority() +  t.getDeadline()  + t.isCompleted());
        }



    }
}
