import model.Task;
import exceptions.InvalidTaskException;
import exceptions.UserNotFoundException;
import controller.TaskController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// simple tests for TaskController
class TaskControllerTest {

    private TaskController controller;

    @BeforeEach
    void setUp() {
        controller = new TaskController();
    }

    // test adding a task and checking it shows up
    @Test
    void testAddTask() throws Exception {
        controller.addTask("testuser", new Task("Test Task", 3, LocalDate.of(2026, 8, 1)));
        var tasks = controller.getTasksByPriority("testuser");
        assertFalse(tasks.isEmpty(), "task list should not be empty after adding");
        assertEquals("Test Task", tasks.peek().getTitle());
    }

    // test deleting a task and checking it's gone
    @Test
    void testDeleteTask() throws Exception {
        controller.addTask("testuser", new Task("To Delete", 2, LocalDate.of(2026, 8, 1)));
        controller.deleteTask("testuser", "To Delete");
        var tasks = controller.getTasksByPriority("testuser");
        assertTrue(tasks.isEmpty(), "task list should be empty after deleting");
    }

    // test that invalid priority throws exception
    @Test
    void testInvalidTaskThrowsException() {
        assertThrows(InvalidTaskException.class, () -> {
            new Task("Bad Task", 10, LocalDate.of(2026, 8, 1));
        });
    }
}
