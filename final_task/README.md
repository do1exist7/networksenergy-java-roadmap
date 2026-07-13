# Console Todo List Application

Simple, robust, and modern Console-based Todo List manager built with Java. This project demonstrates clean architecture, object-oriented design, and the use of modern Java features.

## Key Features
* **Full CRUD Operations:** Create, Read, Update (Toggle Status), and Delete tasks.
* **In-Memory Storage:** Powered by `HashMap` for fast $O(1)$ lookups using unique `UUID` keys.
* **Defensive Input Validation:** Gracefully handles invalid inputs (like text instead of numbers) without crashing.

##  Modern Java Features Used
* **Java Records (`record`):** Used for data immutability (`TodoTask`).
* **Modern Switch Expressions:** Utilized for clean, pattern-matching style command routing.
* **Instance Main Methods:** Leveraged modern Java launch features (`void main()`).
* **Optional API:** Used for safe, null-pointer-resistant data reading.

## Architecture Overview
The project follows the **Separation of Concerns (SoC)** principle:
1. `TodoTask`: The immutable domain model.
2. `TaskService`: The core business logic (pure CRUD data management, UI-agnostic).
3. `TodoUI`: The presentation layer handling user input, formatting, and exception trapping.