package net.novucs.zombieserver.command;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CommandResult {

    private final List<String> target = new LinkedList<>();

    public List<String> get() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandResult that = (CommandResult) o;
        return Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target);
    }
}
