package net.novucs.zombieserver.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents an in game item.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class Item {

    private static final Map<String, Item> BY_NAME = new HashMap<>();

    public static final Item GOLD = Item.of("GOLD", "<img src=\\\"../assets/gold.png\\\">");
    public static final Item KEY = Item.of("KEY", "<img src=\\\"../assets/key.png\\\">");
    public static final Item CHAINSAW = Item.of("CHAINSAW", "<img src=\\\"../assets/chainsaw.png\\\">");
    public static final Item DAISY = Item.of("DAISY", "<img src=\\\"../assets/daisy.png\\\">");

    private final String name;
    private final String html;

    /**
     * Constructs a new item.
     *
     * @param name the item name.
     * @param html the item html.
     */
    private Item(String name, String html) {
        this.name = name;
        this.html = html;
    }

    /**
     * Factory method for creating a new item if none already exists.
     *
     * @param name the item name.
     * @param html the item html.
     * @return the item either found or created.
     */
    public static Item of(String name, String html) {
        name = name.toUpperCase();
        Item item = BY_NAME.get(name);

        if (item != null) {
            return item;
        }

        item = new Item(name, html);
        BY_NAME.put(name, item);
        return item;
    }

    /**
     * Gets an item if it exists.
     *
     * @param name the item to search for.
     * @returnthe item found, or {@code Optional#empty()}.
     */
    public static Optional<Item> get(String name) {
        return Optional.ofNullable(BY_NAME.getOrDefault(name.toUpperCase(), null));
    }

    /**
     * Gets the name of the item.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the html displayed for the item.
     *
     * @return the html.
     */
    public String getHtml() {
        return html;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) &&
                Objects.equals(html, item.html);
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
