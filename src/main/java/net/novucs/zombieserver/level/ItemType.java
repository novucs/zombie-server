package net.novucs.zombieserver.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ItemType {

    private static final Map<String, ItemType> BY_NAME = new HashMap<>();

    public static final ItemType GOLD = ItemType.of("GOLD", "<img src=\\\"../assets/gold.png\\\">");
    public static final ItemType KEY = ItemType.of("KEY", "<img src=\\\"../assets/key.png\\\">");
    public static final ItemType CHAINSAW = ItemType.of("CHAINSAW", "<img src=\\\"../assets/chainsaw.png\\\">");
    public static final ItemType DAISY = ItemType.of("DAISY", "<img src=\\\"../assets/daisy.png\\\">");

    private final String name;
    private final String html;

    private ItemType(String name, String html) {
        this.name = name;
        this.html = html;
    }

    public static ItemType of(String name, String html) {
        if (BY_NAME.containsKey(name)) {
            throw new IllegalStateException("An item type by that name has already been created");
        }

        ItemType itemType = new ItemType(name, html);
        BY_NAME.put(name.toUpperCase(), itemType);
        return itemType;
    }

    public static Optional<ItemType> get(String name) {
        return Optional.ofNullable(BY_NAME.getOrDefault(name.toUpperCase(), null));
    }

    public String getName() {
        return name;
    }

    public String getHtml() {
        return html;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemType itemType = (ItemType) o;
        return Objects.equals(name, itemType.name) &&
                Objects.equals(html, itemType.html);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, html);
    }

    @Override
    public String toString() {
        return "ItemType{" +
                "name='" + name + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}
