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

        System.out.println("\n✅ Phase 4: Tasks added via TaskController");

        System.out.println("\n📋 Tasks sorted by priority (highest first):");
        PriorityQueue<Task> tasks = controller.getTasksByPriority("shayan");
        while (!tasks.isEmpty()) {
            Task t = tasks.poll();
            System.out.println("  - " + t.getTitle() + " (Priority: " + t.getPriority() + ")");
        }

        controller.deleteTask("shayan", "خرید نان");
        System.out.println("\n🗑️ Deleted task: خرید نان");

        controller.updateTask("shayan", "مطالعه جاوا",
                new Task("مطالعه جاوا پیشرفته", 4, LocalDate.of(2026, 8, 15)));
        System.out.println("✏️ Updated task: مطالعه جاوا → مطالعه جاوا پیشرفته (priority 4)");

        System.out.println("\n📋 Remaining tasks by priority:");
        PriorityQueue<Task> remaining = controller.getTasksByPriority("shayan");
        while (!remaining.isEmpty()) {
            Task t = remaining.poll();
            System.out.println("  - " + t.getTitle() + " (Priority: " + t.getPriority() + ")");
        }

        System.out.println("\n✅ Phase 4: Data saved to tasks.txt successfully!");
    }
}
