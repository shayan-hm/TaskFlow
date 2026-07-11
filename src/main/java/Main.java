import java.util.List;
import java.util.PriorityQueue;
import java.io.File;
import model.Task;
import controller.TaskController;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        File dataFile = new File("tasks.txt");
        if (dataFile.exists()) {
            dataFile.delete();
        }

        TaskController controller = new TaskController();

        controller.addTask("shayan", new Task("خرید نان", 2, LocalDate.of(2026, 7, 10)));
        controller.addTask("shayan", new Task("تحویل پروژه", 5, LocalDate.of(2026, 7, 25)));
        controller.addTask("shayan", new Task("مطالعه جاوا", 3, LocalDate.of(2026, 8, 1)));
        controller.addTask("shayan", new Task("ورزش صبحگاهی", 4, LocalDate.of(2026, 7, 15)));

        // mark one task as completed
        controller.getTasksByPriority("shayan").peek();
        List<Task> allTasks = controller.getHighPriorityTasks("shayan", 1);
        allTasks.get(0).setCompleted(true);

        System.out.println("Phase 5: Lambda & Stream API");

        System.out.println("\nAll tasks:");
        controller.printAllTasks("shayan");

        System.out.println("\nHigh priority tasks (priority >= 4):");
        List<Task> highPriority = controller.getHighPriorityTasks("shayan", 4);
        for (Task t : highPriority) {
            System.out.println("- " + t.getTitle() + " (Priority: " + t.getPriority() + ")");
        }

        System.out.println("\nCompleted tasks:");
        List<Task> completed = controller.getCompletedTasks("shayan");
        if (completed.isEmpty()) {
            System.out.println("- no completed tasks");
        } else {
            for (Task t : completed) {
                System.out.println("- " + t.getTitle());
            }
        }
    }
}