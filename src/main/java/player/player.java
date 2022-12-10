package player;

public class player {

    private final String name;
    private final int goal;

    public player(String name, int goal) {
        this.name = name;
        this.goal = goal;
    }

    public String getName() {
        return name;
    }

    public int getGoal() {
        return goal;
    }
}
