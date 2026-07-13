public record TodoTask(UUID id, String title, boolean isCompleted) {}

public class TaskService {
    // "Fake" Database
    private final Map<UUID, TodoTask> database = new HashMap<>();

    // Creation
    public TodoTask createTask(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title can't be empty!");
        var task = new TodoTask(UUID.randomUUID(), title, false);
        database.put(task.id(), task);
        return task;
    }

    // Read
    public Optional<TodoTask> getTask(UUID id) { return Optional.ofNullable(database.get(id)); }
    public List<TodoTask> getAllTasks() { return new ArrayList<>(database.values()); }

    // Update
    public void toggleTask(UUID id) {
        TodoTask task = database.get(id);
        if (task == null) throw new NoSuchElementException("Task not found!");
        database.put(id, new TodoTask(task.id(), task.title(), !task.isCompleted()));
    }

    // Delete
    public void deleteTask(UUID id) { database.remove(id); }
}

void main() {
    var toDoList = new TaskService();
    toDoList.createTask("Watch anime");
    toDoList.createTask("Learn Java");
    IO.println(toDoList.getAllTasks());
}
