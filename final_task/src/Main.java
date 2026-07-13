import java.util.Scanner;

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

public class TodoUI {
    private final TaskService service = new TaskService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            running = switch (choice) {
                case "1" -> handleList();
                case "2" -> handleAdd();
                case "3" -> handleToggle();
                case "4" -> handleDelete();
                case "0" -> false; // Exit the loop
                default -> {
                    IO.println("Unknown command!");
                    yield true;
                }
            };
        }
    }

    public void printMenu(){
        IO.println("What do you want to do? Actions:\n" +
                "1. Get list of to-do tasks\n" +
                "2. Add to-do task\n" +
                "3. Toggle the task\n" +
                "4. Delete the task\n" +
                "0. Exit\n");
    }

    private boolean handleList() {
        var tasks = service.getAllTasks();
        if(tasks.isEmpty()) {
            IO.println("List is empty!");
            return true;
        }
        for (int i = 0; i < tasks.size(); i++) {
            IO.println("%d. %s. Completed: [%s]".formatted(i, tasks.get(i).title(), tasks.get(i).isCompleted() ? "✓" : "✘"));
        }
        return true;
    }

    private boolean handleAdd() {
        IO.print("Enter the name of new task:");
        String title = scanner.nextLine();
        try {
            service.createTask(title);
        } catch (IllegalArgumentException e){
            IO.println(e.getMessage());
        }

        return true;
    }

    private boolean handleToggle() {
        var tasks = service.getAllTasks();
        if (tasks.isEmpty()) {
            IO.println("List is empty!");
            return true;
        }

        for (int i = 0; i < tasks.size(); i++) {
            IO.println("%d. %s".formatted(i, tasks.get(i).title()));
        }

        IO.print("Enter the index to toggle:");

        int idx = Integer.parseInt(scanner.nextLine());

        // checking the boundaries
        if (idx >= 0 && idx < tasks.size()) {
            service.toggleTask(tasks.get(idx).id());
            IO.println("Toggled!");
        } else {
            IO.println("Invalid index!");
        }
        return true;
    }

    private boolean handleDelete() {
        var tasks = service.getAllTasks();
        if (tasks.isEmpty()) {
            IO.println("List is empty!");
            return true;
        }

        for (int i = 0; i < tasks.size(); i++) {
            IO.println("%d. %s".formatted(i, tasks.get(i).title()));
        }

        IO.print("Enter the index to delete:");

        int idx = Integer.parseInt(scanner.nextLine());

        // checking the boundaries
        if (idx >= 0 && idx < tasks.size()) {
            service.deleteTask(tasks.get(idx).id());
            IO.println("Deleted!");
        } else {
            IO.println("Invalid index!");
        }
        return true;
    }

}

void main() {
    new TodoUI().start();
}
