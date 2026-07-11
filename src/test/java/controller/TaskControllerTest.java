package controller;

import model.Task;
import exceptions.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest {

    private TaskController controller;

    @BeforeEach
    void setUp() {
        controller = new TaskController();
    }

    @Test
    void addTaskAndGetTasksByPriority() throws Exception {
        controller.addTask("user1", new Task("Task 1", 3, LocalDate.of(2026, 7, 10)));
        controller.addTask("user1", new Task("Task 2", 5, LocalDate.of(2026, 7, 15)));
        controller.addTask("user1", new Task("Task 3", 1, LocalDate.of(2026, 7, 20)));

        var pq = controller.getTasksByPriority("user1");
        assertEquals(3, pq.size());
        assertEquals("Task 2", pq.poll().getTitle()); // highest priority first
    }

    @Test
    void getTasksByPriorityThrowsIfUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> controller.getTasksByPriority("unknown"));
    }

    @Test
    void deleteTaskRemovesCorrectTask() throws Exception {
        controller.addTask("user1", new Task("Task 1", 3, LocalDate.of(2026, 7, 10)));
        controller.addTask("user1", new Task("Task 2", 5, LocalDate.of(2026, 7, 15)));

        controller.deleteTask("user1", "Task 1");

        var pq = controller.getTasksByPriority("user1");
        assertEquals(1, pq.size());
        assertEquals("Task 2", pq.peek().getTitle());
    }

    @Test
    void deleteTaskThrowsIfUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> controller.deleteTask("unknown", "Task 1"));
    }

    @Test
    void updateTaskReplacesCorrectTask() throws Exception {
        controller.addTask("user1", new Task("Old Title", 3, LocalDate.of(2026, 7, 10)));
        controller.addTask("user1", new Task("Task 2", 5, LocalDate.of(2026, 7, 15)));

        Task updated = new Task("New Title", 4, LocalDate.of(2026, 8, 1));
        controller.updateTask("user1", "Old Title", updated);

        var pq = controller.getTasksByPriority("user1");
        assertEquals(2, pq.size());
        assertEquals("New Title", pq.peek().getTitle()); // highest priority is updated task (4 vs 5)
    }

    @Test
    void updateTaskThrowsIfUserNotFound() throws Exception {
        Task t = new Task("New", 3, LocalDate.of(2026, 7, 10));
        assertThrows(UserNotFoundException.class, () -> controller.updateTask("unknown", "Old", t));
    }

    @Test
    void getCompletedTasksReturnsOnlyCompleted() throws Exception {
        controller.addTask("user1", new Task("Task 1", 3, LocalDate.of(2026, 7, 10)));
        controller.addTask("user1", new Task("Task 2", 5, LocalDate.of(2026, 7, 15)));

        List<Task> all = controller.getHighPriorityTasks("user1", 1);
        all.get(0).setCompleted(true);

        List<Task> completed = controller.getCompletedTasks("user1");
        assertEquals(1, completed.size());
        assertTrue(completed.get(0).isCompleted());
    }

    @Test
    void getCompletedTasksThrowsIfUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> controller.getCompletedTasks("unknown"));
    }

    @Test
    void getHighPriorityTasksFiltersCorrectly() throws Exception {
        controller.addTask("user1", new Task("Low", 2, LocalDate.of(2026, 7, 10)));
        controller.addTask("user1", new Task("Medium", 3, LocalDate.of(2026, 7, 15)));
        controller.addTask("user1", new Task("High", 5, LocalDate.of(2026, 7, 20)));

        List<Task> high = controller.getHighPriorityTasks("user1", 4);
        assertEquals(1, high.size());
        assertEquals("High", high.get(0).getTitle());
    }

    @Test
    void getHighPriorityTasksThrowsIfUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> controller.getHighPriorityTasks("unknown", 1));
    }

    @Test
    void printAllTasksThrowsIfUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> controller.printAllTasks("unknown"));
    }
}