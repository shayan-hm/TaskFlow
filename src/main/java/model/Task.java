package model;

import java.time.LocalDate;
import exceptions.InvalidTaskException;

public class Task implements Comparable<Task> {
    private String title;
    private int priority;
    private LocalDate deadline;
    private boolean completed;



    public Task(String title, int priority, LocalDate deadline) throws InvalidTaskException {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTaskException("عنوان تسک نمی‌تواند خالی باشد!");
        }
        if (priority < 1 || priority > 5) {
            throw new InvalidTaskException("اولویت باید بین 1 تا 5 باشد!");
        }
        if (deadline == null) {
            throw new InvalidTaskException("تاریخ تسک نمی‌تواند خالی باشد!");
        }
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) throws InvalidTaskException {
        if (priority < 1 || priority > 5) {
            throw new InvalidTaskException("اولویت باید بین 1 تا 5 باشد!");
        }
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int compareTo(Task other) {
        if (this.priority > other.priority) {
            return -1;
        } else if (this.priority == other.priority) {
            return 0;
        } else {
            return 1;
        }
    }
}
