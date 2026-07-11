package model;

import exceptions.InvalidTaskException;
import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void validTaskCreation() throws Exception {
        Task t = new Task("Test Task", 3, LocalDate.of(2026, 7, 10));
        assertEquals("Test Task", t.getTitle());
        assertEquals(3, t.getPriority());
        assertEquals(LocalDate.of(2026, 7, 10), t.getDeadline());
        assertFalse(t.isCompleted());
    }

    @Test
    void emptyTitleThrowsException() {
        assertThrows(InvalidTaskException.class, () -> new Task("", 3, LocalDate.of(2026, 7, 10)));
        assertThrows(InvalidTaskException.class, () -> new Task("   ", 3, LocalDate.of(2026, 7, 10)));
    }

    @Test
    void priorityOutOfRangeThrowsException() {
        assertThrows(InvalidTaskException.class, () -> new Task("Task", 0, LocalDate.of(2026, 7, 10)));
        assertThrows(InvalidTaskException.class, () -> new Task("Task", 6, LocalDate.of(2026, 7, 10)));
    }

    @Test
    void nullDeadlineThrowsException() {
        assertThrows(InvalidTaskException.class, () -> new Task("Task", 3, null));
    }

    @Test
    void setPriorityValid() throws Exception {
        Task t = new Task("Task", 3, LocalDate.of(2026, 7, 10));
        t.setPriority(5);
        assertEquals(5, t.getPriority());
    }

    @Test
    void setPriorityInvalidThrowsException() throws Exception {
        Task t = new Task("Task", 3, LocalDate.of(2026, 7, 10));
        assertThrows(InvalidTaskException.class, () -> t.setPriority(0));
        assertThrows(InvalidTaskException.class, () -> t.setPriority(6));
    }

    @Test
    void setCompletedWorks() throws Exception {
        Task t = new Task("Task", 3, LocalDate.of(2026, 7, 10));
        assertFalse(t.isCompleted());
        t.setCompleted(true);
        assertTrue(t.isCompleted());
    }

    @Test
    void compareToOrdersByPriorityDescending() throws Exception {
        Task high = new Task("High", 5, LocalDate.of(2026, 7, 10));
        Task low = new Task("Low", 1, LocalDate.of(2026, 7, 10));
        assertTrue(high.compareTo(low) < 0); // higher priority comes first
    }
}