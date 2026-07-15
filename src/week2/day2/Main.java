package week2.day2;

import java.util.*;

class Player implements Comparable<Player>{
    private String username;
    private int score;
    public Player(String username, int score){
        this.username = username;
        this.score = score;
    }
    public String getUsername(){
        return this.username;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Player other){
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

public class Main {
public static void main(String[] args) {
    HashSet<Player> registry = new HashSet<>();

    registry.add(new Player("AnimeFanGirl", 500));
    registry.add(new Player("DumbPeasant", 900));
    registry.add(new Player("ExCucumber", 900)); // Ties score with DumbPeasant, but higher level!
    registry.add(new Player("AnimeFanGirl", 500)); // Duplicate! Will be ignored.

    IO.println("Registry Size: " + registry.size());

    Map<String, Player> playerDatabase = new HashMap<>();
    for (var player : registry) {
        playerDatabase.put(player.getUsername(), player);
    }

    if (playerDatabase.containsKey("ExCucumber")) {
        IO.println("Found Profile: " + playerDatabase.get("ExCucumber"));
    }

    List<Player> leaderboard = new ArrayList<>(playerDatabase.values());

    leaderboard.sort(Comparator.naturalOrder());


    IO.println("\nTOURNAMENT LEADERBOARD");
    leaderboard.forEach(IO::println);

    IO.println("\n");
    for (var entry : playerDatabase.entrySet()) {
        IO.println("Key: " + entry.getKey() + " -> Value: " + entry.getValue());
    }
}
}
