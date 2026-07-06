class Player implements Comparable<Player>{
    private String username;
    private int score, level;
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

void main() {
    HashSet<Player> registry = new HashSet<>();
    registry.add(new Player("AnimeFanGirl", 500));
    registry.add(new Player("DumbPeasant", 900));
    registry.add(new Player("ExCucumber", 900));
    registry.add(new Player("AnimeFanGirl", 500));

    IO.println(registry.toString());
}
