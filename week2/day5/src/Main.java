import java.util.*;
import java.util.stream.Collectors;

class Player implements Comparable<Player> {
    private String username;
    private int score;

    public Player(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() { return this.username; }
    public int getScore() { return this.score; }

    @Override
    public int compareTo(Player other) {
        return Integer.compare(other.getScore(), this.getScore());
    }

    @Override
    public String toString() {
        return "Username %s, Score %d;".formatted(this.username, this.score);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return Objects.equals(username, player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.username);
    }
}

enum Rarity { COMMON, EPIC, LEGENDARY }
record Item(String name, Rarity rarity, int weight, int goldValue) {}

class InsufficientIngredientsException extends RuntimeException {
    public InsufficientIngredientsException(String s) { super(s); }
}

class InsufficientGoldException extends RuntimeException {
    public InsufficientGoldException(String s) { super(s); }
}

class Hero extends Player {
    private final List<Item> backpack = new ArrayList<>();
    private int gold;

    public Hero(String username, int startingGold) {
        super(username, 0);
        this.gold = startingGold;

        backpack.add(new Item("Knife", Rarity.COMMON, 10, 1));
        backpack.add(new Item("Potion A", Rarity.COMMON, 10, 1));
        backpack.add(new Item("Potion B", Rarity.COMMON, 10, 1));
        backpack.add(new Item("Potion C", Rarity.COMMON, 10, 1));
    }

    public Optional<Item> findItemByName(String name) {
        return this.backpack.stream()
                .filter(item -> item.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public int totalBackpackWeight() {
        return this.backpack.stream()
                .map(Item::weight)
                .reduce(0, Integer::sum);
    }

    public Map<Rarity, List<Item>> groupByRarity() {
        return this.backpack.stream()
                .collect(Collectors.groupingBy(Item::rarity));
    }

    public void addToBackpack(Item item) {
        this.backpack.add(item);
    }

    public void craft(String recipe) {
        try {
            Item craftedItem = switch (recipe) {
                case "SuperPotion" -> {
                    long potionCount = this.backpack.stream()
                            .filter(x -> x.name().contains("Potion"))
                            .count();

                    if (potionCount < 3) throw new InsufficientIngredientsException("нужно как минимум 3 зелья!");
                    if (this.gold < 100) throw new InsufficientGoldException("нужно как минимум 100 золота!");

                    this.gold -= 100;
                    yield new Item("SuperPotion", Rarity.EPIC, 30, 150);
                }
                default -> throw new IllegalArgumentException("Рецепт не найден!");
            };

            this.backpack.add(craftedItem);
            IO.println("Успешно скрафчено: " + craftedItem.name());

        } catch (IllegalArgumentException | InsufficientGoldException | InsufficientIngredientsException e) {
            IO.println("Ошибка крафта: " + e.getMessage());
        }
    }

    public void printStatus() {
        IO.println("Инвентарь героя " + getUsername() + " (Золото: " + gold + "): " + backpack);
    }
}

void main() {
    HashSet<Player> registry = new HashSet<>();
    registry.add(new Player("AnimeFanGirl", 200));
    registry.add(new Player("DumbPeasant", 460));
    registry.add(new Player("ExCucumber", 900));
    registry.add(new Player("AnimeFanGirl", 500)); // Дубликат, проигнорируется!

    IO.println("Registry Size (Ожидаем 3): " + registry.size());

    List<String> eliteUsernames = registry.stream()
            .filter(x -> x.getScore() > 450)
            .map(Player::getUsername)
            .collect(Collectors.toList());

    IO.println("Элитные игроки: " + eliteUsernames);

    var himmel = new Hero("Himmel", 150);
    himmel.printStatus();

    IO.println("Общий вес рюкзака: " + himmel.totalBackpackWeight());

    himmel.craft("SuperPotion");
    himmel.printStatus();

    IO.println("Группировка по редкости: " + himmel.groupByRarity());

    himmel.craft("SuperPotion");
    himmel.findItemByName("Potion A")
            .ifPresentOrElse(
                    item -> IO.println("Экипировано: " + item.name()),
                    () -> IO.println("У вас нет такого оружия!")
            );
}