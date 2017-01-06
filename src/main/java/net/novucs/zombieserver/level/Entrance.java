package net.novucs.zombieserver.level;

import java.util.Objects;

public class Entrance {

    private final Direction direction;
    private final Room to;
    private boolean locked;

    public Entrance(Direction direction, Room to, boolean locked) {
        this.direction = direction;
        this.to = to;
        this.locked = locked;
    }

    public Direction getDirection() {
        return direction;
    }

    public Room getTo() {
        return to;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrance entrance = (Entrance) o;
        return locked == entrance.locked &&
                direction == entrance.direction &&
                Objects.equals(to, entrance.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, to, locked);
    }

    @Override
    public String toString() {
        return "Entrance{" +
                "direction=" + direction +
                ", to=" + to +
                ", locked=" + locked +
                '}';
    }
}
