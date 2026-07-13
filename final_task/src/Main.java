import java.util.Scanner;

public record TodoTask(UUID id, String title, boolean isCompleted) {}

public interface TaskRepository {
    void save(TodoTask task);
    Optional<TodoTask> findById(UUID id);
    List<TodoTask> findAll();
    void deleteById(UUID id);
}

public class InMemoryTaskRepository implements TaskRepository{
    private final Map<UUID, TodoTask> database = new HashMap<>();

    @Override
    public void save(TodoTask task) {
        database.put(task.id(), task);
    }

    @Override
    public Optional<TodoTask> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<TodoTask> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(UUID id) {
        database.remove(id);
    }
}

/**
 * Сервис для управления To-Do листом через CRUD операции.
 */
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * Создает новую задачу и сохраняет ее в базу данных.
     * * @param title Название задачи (не должно быть пустым)
     * @return Созданный объект TodoTask с уникальным UUID
     * @throws IllegalArgumentException если название задачи пустое или null
     */
    public TodoTask createTask(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title can't be empty!");
        var task = new TodoTask(UUID.randomUUID(), title, false);
        repository.save(task);
        return task;
    }

    /**
     * Возвращает задачу по её ID.
     * @param id уникальный идентификатор задачи
     * @return Optional с задачей, или Optional.empty(), если задача не найдена
     */
    public Optional<TodoTask> getTask(UUID id) { return repository.findById(id);}
    public List<TodoTask> getAllTasks() { return repository.findAll();}

    /**
     * Переключает статус выполнения задачи (выполнено / не выполнено).
     * * @param id Уникальный идентификатор задачи
     * @throws NoSuchElementException если задача с таким ID не найдена
     */
    public void toggleTask(UUID id) {
        TodoTask updatedTask = repository.findById(id)
                .map(task -> new TodoTask(task.id(), task.title(), !task.isCompleted()))
                .orElseThrow(() -> new NoSuchElementException("Task not found!"));
        repository.save(updatedTask);
    }

    /**
     * Удаляет задачу из базы данных.
     * @param id уникальный идентификатор задачи на удаление
     */
    public void deleteTask(UUID id) { repository.deleteById(id);}
}

public class TodoUI {
    private final TaskService service = new TaskService(new InMemoryTaskRepository());
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
        IO.print("""
            What do you want to do?
            1. Get list of to-do tasks
            2. Add to-do task
            3. Toggle the task
            4. Delete the task
            0. Exit
            
            Enter choice:""");
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

        int idx = readValidIndex();

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

        int idx = readValidIndex();

        // checking the boundaries
        if (idx >= 0 && idx < tasks.size()) {
            service.deleteTask(tasks.get(idx).id());
            IO.println("Deleted!");
        } else {
            IO.println("Invalid index!");
        }
        return true;
    }


    private int readValidIndex() {
        // Читаем всю строку и парсим в int.
        // Это защищает от бага Scanner.nextInt(), который оставляет символ новой строки '\n' в буфере.
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Return an invalid index that fails boundary checks in the handleToggle() and handleDelete()
        }
    }

}

void main() {
    new TodoUI().start();
}
